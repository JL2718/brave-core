/* Copyright (c) 2024 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/. */

package org.chromium.chrome.browser.vpn.activities;

import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.chromium.base.supplier.OneshotSupplier;
import org.chromium.chrome.R;
import org.chromium.chrome.browser.init.ActivityProfileProvider;
import org.chromium.chrome.browser.init.AsyncInitializationActivity;
import org.chromium.chrome.browser.profiles.ProfileProvider;
import org.chromium.chrome.browser.vpn.adapters.AlwaysOnPagerAdapter;
import org.chromium.chrome.browser.vpn.utils.BraveVpnUtils;

public class VpnAlwaysOnActivity extends AsyncInitializationActivity {
    private void initializeViews() {
        setContentView(R.layout.activity_auto_reconnect_vpn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.auto_reconnect_vpn));

        ViewPager autoReconnectVpnTutorialViewPager =
                findViewById(R.id.auto_reconnect_vpn_tutorial_view_pager);

        AlwaysOnPagerAdapter alwaysOnPagerAdapter = new AlwaysOnPagerAdapter(this);
        autoReconnectVpnTutorialViewPager.setAdapter(alwaysOnPagerAdapter);
        TabLayout autoReconnectVpnTutorialTabLayout =
                findViewById(R.id.auto_reconnect_vpn_tutorial_tab_layout);
        autoReconnectVpnTutorialTabLayout.setupWithViewPager(
                autoReconnectVpnTutorialViewPager, true);

        Button autoReconnectVpnAction = findViewById(R.id.auto_reconnect_vpn_action_button);
        autoReconnectVpnAction.setOnClickListener(
                v -> {
                    BraveVpnUtils.openVpnSettings(VpnAlwaysOnActivity.this);
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void triggerLayoutInflation() {
        initializeViews();
        onInitialLayoutInflationComplete();
    }

    @Override
    public boolean shouldStartGpuProcess() {
        return true;
    }

    @Override
    protected OneshotSupplier<ProfileProvider> createProfileProvider() {
        return new ActivityProfileProvider(getLifecycleDispatcher());
    }
}
