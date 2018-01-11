package com.client.sportika.dsl

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.*
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothAdapter
import android.content.*
import android.content.pm.LauncherApps
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.*
import android.os.storage.StorageManager
import android.print.PrintManager
import android.service.wallpaper.WallpaperService
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.NotificationCompat
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import android.widget.*
import cn.pedant.SweetAlert.SweetAlertDialog
import com.github.ajalt.timberkt.Timber
import com.webcubictechnologies.remitnow.koroutines.UI
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.launch


infix fun Context?.toast(message: CharSequence?) {
    Toast.makeText(this ?: return, message ?: "Unknown message", Toast.LENGTH_SHORT).show()
}

infix fun Context?.TOAST(message: CharSequence?) {
    Toast.makeText(this ?: return, message ?: "Unknown message", Toast.LENGTH_LONG).show()
}

infix fun Bundle?.save(op: Bundle.() -> Unit) {
    val bundle = Bundle()
    bundle.op()
}

infix fun SharedPreferences.save(op: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.op()
    editor.apply()
}

infix fun SharedPreferences.Editor.insert(pair: Pair<String, Any>) {
    when (pair.second) {
        is String -> putString(pair.first, pair.second as String)
        is Int -> putInt(pair.first, pair.second as Int)
        is Boolean -> putBoolean(pair.first, pair.second as Boolean)
        is Float -> putFloat(pair.first, pair.second as Float)
        is Long -> putLong(pair.first, pair.second as Long)
    }
}

infix fun Bundle.insertObjects(pair: Pair<String, Any>) {
    when (pair.second) {
        is String -> putString(pair.first, pair.second as String)
        is Int -> putInt(pair.first, pair.second as Int)
        is Float -> putFloat(pair.first, pair.second as Float)
        is Boolean -> putBoolean(pair.first, pair.second as Boolean)
        is Long -> putLong(pair.first, pair.second as Long)
        is Double -> putDouble(pair.first, pair.second as Double)
    }
}

infix fun View.onclick(f: (View) -> Unit) {
    this.setOnClickListener(f)
}

infix fun View.onlongClick(f: (View) -> Boolean) {
    this.setOnLongClickListener(f)
}

infix fun View.ontouch(f: (View, MotionEvent) -> Boolean) {
    this.setOnTouchListener(f)
}

infix fun View.ondrag(f: (View, DragEvent) -> Boolean) {
    this.setOnDragListener(f)
}

infix fun View.onitemclicked(f: (View, Int, KeyEvent) -> Boolean) {
    this.setOnKeyListener(f)
}

infix fun View.onfocuschange(f: (View, Boolean) -> Unit) {
    this.setOnFocusChangeListener(f)
}

infix fun CompoundButton.checkchanged(f: (CompoundButton, Boolean) -> Unit) {
    this.setOnCheckedChangeListener(f)
}

infix fun AdapterView<*>.onitemclicked(f: (AdapterView<*>, View, Int, Long) -> Unit) {
    this.setOnItemClickListener(f)
}

infix fun AdapterView<*>.onitemlongclick(f: (AdapterView<*>, View, Int, Long) -> Boolean) {
    this.setOnItemLongClickListener(f)
}

infix fun <T : AbsListView> T.scrollchange(statechange: (View, Int) -> Unit) {
    val listener = object : AbsListView.OnScrollListener {
        override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        }

        override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
            statechange(view, scrollState)
        }

    }
    this.setOnScrollListener(listener)
}


infix fun EditText.ontextchange(f: (CharSequence, Int, Int, Int) -> Unit) {
    val listener = object : TextWatcher by DelegateTextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            f(s, start, before, count)
        }
    }
    this.addTextChangedListener(listener)
}

infix fun TextView.ontextchange(f: (CharSequence, Int, Int, Int) -> Unit) {
    val listener = object : TextWatcher by DelegateTextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            f(s, start, before, count)
        }
    }
    this.addTextChangedListener(listener)
}


infix fun SeekBar.progresschange(f: (SeekBar, Int, Boolean) -> Unit) {
    val listener = object : SeekBar.OnSeekBarChangeListener by DelegateSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            f(seekBar, progress, fromUser)
        }
    }

    this.setOnSeekBarChangeListener(listener)
}

infix fun (() -> Any).unless(condition: Boolean) {
    if (!condition) {
        this()
    }
}

inline fun fireActivity(context: Context, target: Class<*>, func: Intent.() -> Unit) {
    val intent = Intent(context, target).apply {
        func()
    }
    context.startActivity(intent)
}

inline fun fireForResult(activity: Activity, target: Class<*>, requestCode: Int, func: Intent.() -> Unit) {
    val intent = Intent(activity, target).apply(func)
    activity.startActivityForResult(intent, requestCode)

}

inline fun fireService(context: Context, service: Class<*>, func: Intent.() -> Unit) {
    val intent = Intent(context, service).apply(func)
    context.startService(intent)
}


inline infix fun SQLiteDatabase.transaction(operation: SQLiteDatabase.() -> Unit) {
    beginTransaction()
    try {
        operation()
    } catch (e: SQLiteException) {
        Timber.e { "Exception raised : ${e.message}" }
    } finally {
        endTransaction()
        Timber.d { "Transaction completed" }
    }
}

infix fun Intent.saveString(pair: Pair<String, String>) = putExtra(pair.first, pair.second)

infix fun Intent.saveInt(pair: Pair<String, Int>) = putExtra(pair.first, pair.second)

infix fun Intent.saveLong(pair: Pair<String, Long>) = putExtra(pair.first, pair.second)

infix fun Intent.saveFloat(pair: Pair<String, Float>) = putExtra(pair.first, pair.second)

infix fun Intent.saveBoolean(pair: Pair<String, Boolean>) = putExtra(pair.first, pair.second)

infix fun Intent.saveBundle(pair: Pair<String, Bundle>) = putExtra(pair.first, pair.second)

infix fun Intent.getString(key: String) = getStringExtra(key)

infix fun Intent.getInt(key: String) = getIntExtra(key, 0)

infix fun Intent.getLong(key: String) = getLongExtra(key, 0L)

infix fun Intent.getFloat(key: String) = getFloatExtra(key, 0F)

infix fun Intent.getBoolean(key: String) = getBooleanExtra(key, false)

infix fun Intent.getBundle(key: String) = getBundleExtra(key)

inline fun notification(context: Context, func: NotificationCompat.Builder.() -> Unit): Notification {
    val builder = NotificationCompat.Builder(context)
    builder.func()
    return builder.build()
}

@RequiresApi(Build.VERSION_CODES.O)
inline fun channelNotification(context: Context, channelId: String, func: NotificationCompat.Builder.() -> Unit): Notification {
    val notification = NotificationCompat.Builder(context, channelId)
    notification.func()
    return notification.build()
}


/**
 * Use for sending a notification
 * @param context, noticationId.
 * pass all the notification builder methods here
 * including pendingIntent and taskstackbuilder if needed
 * @Deprecated in api 26
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
@Deprecated("Deprecated in api 26, for api 26 Use notification.displayNotification(context, id, channelId) instead", level = DeprecationLevel.WARNING)
inline fun Notification.Builder.sendNotification(context: Context, notificationId: Int, func: Notification.Builder.() -> Unit) {
    func()
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(notificationId, this.build())
}

/**
 * Use for sending notification
 * @param context, notificationId, channelId
 * pass all the method except setChannelId the method
 * handles it for you
 * use it instead of sendNotification if you are supporting
 * @api 26
 */
@RequiresApi(Build.VERSION_CODES.O)
inline fun Notification.Builder.displayNotification(context: Context, notificationId: Int, channelId: String, func: Notification.Builder.() -> Unit) {
    func()
    this.setChannelId(channelId)
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(notificationId, this.build())
}

fun Context.getAccesibilityManager(): AccessibilityManager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

fun Context.getAccountManager(): AccountManager = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager

fun Context.getActivityManager(): ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

fun Context.getAlarmManager(): AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.getAppWidgetManager(): AppWidgetManager = getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun Context.getAppOpsManager(): AppOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

fun Context.getAudioManager(): AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.getBatteryManager(): BatteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
@SuppressLint("ServiceCast")
fun Context.getBluetoothAdapter(): BluetoothAdapter = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothAdapter

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.getCameraManager(): CameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun Context.getCaptionManager(): CaptioningManager = getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager

fun Context.getClipboardManager(): ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

fun Context.getConnectivityService(): ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun Context.getConsumerIrManager(): ConsumerIrManager = getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

fun Context.devicePolicyManager(): DevicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
fun Context.displayManager(): DisplayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

fun Context.downloadManager(): DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

fun Context.dropBoxManager(): DropBoxManager = getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager

fun Context.inputMethodManager(): InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun Context.inputManager(): InputManager = getSystemService(Context.INPUT_SERVICE) as InputManager

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.jobScheduler(): JobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

fun Context.keyGuardManager(): KeyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.launcherApp(): LauncherApps = getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

fun Context.layoutInflator(): LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Context.locationManager(): LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.mediaProgectionManager(): MediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun Context.mediaRouter(): MediaRouter = getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.mediaSessionManager(): MediaSessionManager = getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager

fun Context.nfcManager(): NfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun Context.nsdManager(): NsdManager = getSystemService(Context.NSD_SERVICE) as NsdManager

fun Context.poerManager(): PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun Context.printService(): PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.restictionManager(): RestrictionsManager = getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager

fun Context.getSearchManager(): SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

fun Context.getSensorManager(): SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

fun Context.getStorageManager(): StorageManager = getSystemService(Context.STORAGE_SERVICE) as StorageManager

fun Context.getTelephonyManager(): TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

fun Context.getTextServicesManager(): TextServicesManager = getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.getTvInputManager(): TvInputManager = getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager

fun Context.getUiModeManager(): UiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager

fun Context.getUsbManager(): UsbManager = getSystemService(Context.USB_SERVICE) as UsbManager

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
fun Context.getUserManager(): UserManager = getSystemService(Context.USER_SERVICE) as UserManager

fun Context.getVibrator(): Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

@SuppressLint("ServiceCast")
fun Context.getWallpaperService(): WallpaperService = getSystemService(Context.WALLPAPER_SERVICE) as WallpaperService

fun Context.getWifiP2pManager(): WifiP2pManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager

@SuppressLint("WifiManagerLeak")
fun Context.getWifiManager(): WifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager

fun Context.getWindowService(): WindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

object DelegateTextWatcher : TextWatcher {
    override fun afterTextChanged(p0: Editable?) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}

object DelegateSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}
    override fun onStartTrackingTouch(p0: SeekBar?) {}
    override fun onStopTrackingTouch(p0: SeekBar?) {}
}

inline fun viewpagerclassAdapter(framgmentList: List<Fragment>, titles: List<String?>?, fm: FragmentManager, f: ViewPagerAdapter.() -> Unit): ViewPagerAdapter = ViewPagerAdapter(framgmentList, titles, fm).apply(f)

inline fun progressDialog(context: Context, func: SweetAlertDialog.() -> Unit): SweetAlertDialog {
    val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
    sweetAlertDialog.func()
    return sweetAlertDialog
}

inline fun normalDialog(context: Context, func: SweetAlertDialog.() -> Unit): SweetAlertDialog {
    val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
    sweetAlertDialog.func()
    return sweetAlertDialog
}


inline fun errorDialog(context: Context, func: SweetAlertDialog.() -> Unit): SweetAlertDialog {
    val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
    sweetAlertDialog.func()
    return sweetAlertDialog
}

inline fun successDialog(context: Context, func: SweetAlertDialog.() -> Unit): SweetAlertDialog {
    val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
    sweetAlertDialog.func()
    return sweetAlertDialog
}

inline fun customImageType(context: Context, func: SweetAlertDialog.() -> Unit): SweetAlertDialog {
    val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
    sweetAlertDialog.func()
    return sweetAlertDialog
}

inline fun warningDialog(context: Context, func: SweetAlertDialog.() -> Unit): SweetAlertDialog {
    val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
    sweetAlertDialog.func()
    return sweetAlertDialog
}

inline fun dialog(context: Context, func: SweetAlertDialog.() -> Unit): SweetAlertDialog {
    val sweetAlertDialog = SweetAlertDialog(context)
    sweetAlertDialog.func()
    return sweetAlertDialog
}

inline fun (() -> Any).orelse(condition: Boolean, func: () -> Any) {
    if (!condition) {
        this()
    } else {
        func()
    }
}

fun Activity.wait(segment: Int, func: () -> Unit) {
    { Handler().postDelayed(func, segment.toLong() * 1000)}
            .orelse(Looper.myLooper() == Looper.getMainLooper()) {
                Thread.sleep(segment.toLong() * 1000)
                func()

            }
}

fun Activity.isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()


fun <T>work(func: () -> Deferred<T>): T? {
    var any: T? = null
    launch(UI) {
        any = func().await()
    }
    return any
}


class ViewPagerAdapter(private val framgmentList: List<Fragment>, private var titles: List<String?>? = null, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int) = framgmentList[position]
    override fun getCount() = framgmentList.size
    override fun getPageTitle(position: Int) = titles?.get(position)
}
