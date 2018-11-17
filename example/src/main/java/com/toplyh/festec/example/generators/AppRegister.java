package com.toplyh.festec.example.generators;

import com.toplyh.latte.annotations.AppRegisterGenerator;
import com.toplyh.latte.core.weichat.templates.AppRegisterTemplate;

@AppRegisterGenerator(
        packageName = "com.toplyh.festec.example",
        registerTemplete = AppRegisterTemplate.class
)
public interface AppRegister {
}
