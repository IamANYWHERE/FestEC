package com.toplyh.latte.core.fragments;

public abstract class LatteDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }
}
