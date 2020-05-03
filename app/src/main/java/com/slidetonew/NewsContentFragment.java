package com.slidetonew;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.supercharge.shimmerlayout.ShimmerLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsContentFragment extends Fragment {
    private static final String TAG = "NewsContentFragment";
    private WebView webView;
    private ImageButton shareImageButton;
    private String content;
    private TextView titleOnFragment;
    private String title;
    private String time;
    private String publish;
    private boolean isDarkMode = BaseApplication.getDarkModeStatus();

    //字体颜色适配暗黑模式
    private String getBg(){
        return isDarkMode? "p{\n" +
                "      color:#fff;\n" +
                "      font-size:20px;\n" +
                "    }" : "p{\n" +
                "      color:#000;\n" +
                "      font-size:20px;\n" +
                "    }";
    }

    //    private String imageToBlank = "";

    public NewsContentFragment() {
        // Required empty public constructor
    }
    @SuppressLint("HandlerLeak")
    private
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                webView.getSettings().setLoadWithOverviewMode(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }
                String contentHead1 = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<style type=\"text/css\">\n" +
                        "    video{\n" +
                        "      width:100%;\n" +
                        "      height:auto;\n" +
                        "    }\n";
                String contentHead2 = "  </style>" +
                        "<meta charset=\"utf-8\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<p style=\"text-align:center\"><b>" + title + "</b></p>\n" +
                        "      <p style=\"text-align:center; color:gray ;font-size:15px\">" + time+ "&nbsp; | &nbsp; "+ publish +"</p>";
                String contentTail = "<br><p style=\"text-align:center; color:gray ;font-size:20px\">没有更多内容了</p>" +"</body>\n" +
                        "</html>";
                String fullContent = contentHead1  + getBg() + contentHead2 + msg.obj +content + contentTail;
                Log.d(TAG, "handleMessage: " + fullContent);
                webView.loadData(fullContent, "text/html", "UTF-8");
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_content, container, false);
        webView = view.findViewById(R.id.webView);
        shareImageButton = view.findViewById(R.id.shareImageButton);
        titleOnFragment = view.findViewById(R.id.titleOnFragment);
        if (isDarkMode){
            webView.setBackgroundColor(Color.rgb(66,66,66));
        } else {
            webView.setBackgroundColor(Color.rgb(246,246,246));
        }
        return view;
    }

    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getArguments() != null;
        titleOnFragment.setText(">>" + getArguments().getString("contentTitle"));
        new Thread(() -> {
            String imageAdd = "";
            try {
                assert getArguments() != null;
                Document doc = Jsoup.connect(getArguments().getString("contentUrl")).get();
                doc.select("div.bot_word").select("div.more-client").select("div.iconfont").remove();
                Element contentEle = doc.select("div.page").select("div.js-page").select("div.on").first();
                //去除视频下的标签
                if (contentEle.select(".bot_word.more-client.iconfont").first() != null){
                    contentEle.select(".bot_word.more-client.iconfont").first().remove();
                }
                if (contentEle.select("p.otitle").first() != null){
                    contentEle.select("p.otitle").first().remove();
                }
                if (contentEle.select("div.photo").toString().equals("") && contentEle.select("div.video").toString().equals("")){
                    imageAdd = "<p><img width = \"100%\" height=\"auto\" src=\""+ getArguments().getString("imageUrl") + "\"" + "/img>";
                    Log.d(TAG, "onActivityCreated: no image");
                }
                content = contentEle.toString().replaceAll("<a", " <img width=\"100%\" height=\"auto\"");
                content = content.replace("href", "src");
            } catch (IOException e) {
                e.printStackTrace();
            }
            title = getArguments().getString("contentTitle");
            time = getArguments().getString("contentTime").substring(0, 16);
            publish = getArguments().getString("contentSource");
            if ((publish.substring(publish.length()-1).equals("#"))){
                publish = publish.substring(0, publish.length()-1);
            }
            Message msg = new Message();
            msg.what = 1;
            msg.obj = imageAdd;
            handler.sendMessage(msg);
        }).start();

        //分享
        shareImageButton.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String shareContent = "\"" + getArguments().getString("contentSource") + "\"报道，"
                    + "[" + getArguments().getString("contentTitle") + "] "
                    + getArguments().getString("contentTime").substring(0,9)
                    + "\n" + "详情查看 " + getArguments().getString("contentUrl");
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);

        });
    }
}
