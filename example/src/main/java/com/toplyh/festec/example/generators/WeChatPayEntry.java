package com.toplyh.festec.example.generators;

import com.toplyh.latte.annotations.PayEntryGenerator;
import com.toplyh.latte.core.weichat.templates.WXPayEntryTemplate;

@PayEntryGenerator(
        packageName = "com.toplyh.festec.example",
        payEntryTemplete = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
