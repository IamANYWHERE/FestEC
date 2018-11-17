package com.toplyh.festec.example.generators;

import com.toplyh.latte.annotations.EntryGenerator;
import com.toplyh.latte.core.weichat.templates.WXEntryTemplate;

@EntryGenerator(
        packageName = "com.toplyh.festec.example",
        entryTemplete = WXEntryTemplate.class
)
public interface WeChatEntry {
}
