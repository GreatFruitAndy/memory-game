package com.snatik.matches.utils;

import android.util.Log;

import org.greatfruit.andy.protobuf.RemoteController;
import org.greatfruit.andy.sdk.presentation.AndyFragment;
import org.greatfruit.andy.sdk.presentation.listeners.AndyFragmentListener;
import org.greatfruit.andy.sdk.session.Session;
import org.greatfruit.andy.sdk.session.SessionProfileBuilder;

public class AndyFragmentLauncher {
    public static void launch(
            final AndyFragment andyFragment,
            final Runnable disconnectRunnable,
            Integer width,
            Integer height) {
        SessionProfileBuilder builder = Session.getBuilder();

        builder.setUuid("org.greatfruit.andy.AndroidSDKExample")
                .setName("advert example")
                .setUrl("10.13.37.12")
                .setVideoFps(45)
                .setSize(width, height);

        andyFragment.init(
                builder,
                new AndyFragmentListener() {
                    @Override
                    public void onConnected() {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                andyFragment.disconnect();
                            }
                        }.start();
                    }

                    @Override
                    public void onDisconnected() {
                        disconnectRunnable.run();
                    }

                    @Override
                    public String onAuthRequest(int i) {
                        Log.i(getClass().getSimpleName(), "For some of reason, I need to write password (it is wrong).");
                        return null;
                    }

                    @Override
                    public void onConnectionError() {}

                    @Override
                    public void onKeepAlive(RemoteController.KeepAlive keepAlive) {}
                }
        );
    }
}
