package org.dionisio.task.app.core.utils.platform

import platform.UIKit.UIApplication

actual fun getAppName(): String {
    val appDelegate = UIApplication.sharedApplication.delegate
    val window = appDelegate?.window
    val rootVC = window?.rootViewController
    return rootVC?.title ?: "iOS App"
}