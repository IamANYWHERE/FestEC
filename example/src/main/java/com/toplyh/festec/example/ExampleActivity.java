package com.toplyh.festec.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.toplyh.latte.core.activities.ProxyActivity;
import com.toplyh.latte.core.app.Latte;
import com.toplyh.latte.core.delegates.LatteDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
