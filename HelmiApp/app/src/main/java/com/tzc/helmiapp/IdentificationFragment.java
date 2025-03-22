package com.tzc.helmiapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

public class IdentificationFragment extends Fragment {
    private static final String STREAM_URL = "http://172.20.10.2:81/stream";
    private static final String WS_URL = "ws://helmi.asia/ws/detection";

    private WebView webView;
    private WebSocketClient webSocketClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_identification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.webView);
        setupWebView();
        setupWebSocket();
    }

    private void setupWebView() {
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(STREAM_URL);
    }

    private void setupWebSocket() {
        try {
            webSocketClient = new WebSocketClient(new URI(WS_URL)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "WebSocket 已连接", Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onMessage(String message) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "WebSocket 收到消息: " + message, Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "WebSocket 断开: " + reason, Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onError(Exception ex) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "WebSocket 错误: " + ex.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}