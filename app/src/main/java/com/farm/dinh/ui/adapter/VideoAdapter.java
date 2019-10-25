package com.farm.dinh.ui.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.farm.dinh.R;
import com.farm.dinh.data.model.Video;
import com.farm.dinh.helper.UIHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<Video> videoList = new ArrayList<>();

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.initData(videoList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    protected static class VideoViewHolder extends RecyclerView.ViewHolder {
        private WebView playerView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.videoYoutube);
            playerView.setWebChromeClient(new WebChromeClient() {
                @Override
                public Bitmap getDefaultVideoPoster() {
                    return Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
                }
            });
            new UIHelper.CustomWebViewClient(playerView, true);
        }

        public void initData(Video video) {
            playerView.loadDataWithBaseURL("", getDataWebView(video),
                    "text/html", "UTF-8", "");
        }

        public static String getDataWebView(Video video) {
            String data = "<iframe allowfullscreen=\"\" frameborder=\"0\" height=\"540\" src=\"https://www.youtube.com/embed/" + getYoutubeId(video.getLink()) + "\" width=\"960\"></iframe>";
            return data;
        }

        public static String getYoutubeId(String url) {
            String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

            Pattern compiledPattern = Pattern.compile(pattern,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = compiledPattern.matcher(url);
            if (matcher.find()) {
                return matcher.group(1);
            }/*from w  w  w.  j a  va  2 s .c om*/
            return null;
        }
    }
}
