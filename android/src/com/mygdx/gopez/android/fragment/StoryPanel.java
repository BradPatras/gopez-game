package com.mygdx.gopez.android.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mygdx.gopez.android.R;
import com.mygdx.gopez.android.TypeWriter;

public class StoryPanel extends Fragment implements TypeWriter.FinishListener{

    public Listener mListener;
    private String message;
    private Bitmap image;
    private long startTime;

    private ImageView panelImage;
    private TypeWriter panelText;

    public StoryPanel() {
        // Required empty public constructor
    }

    public static StoryPanel newInstance(String message, Bitmap image, Listener listener) {
        StoryPanel fragment = new StoryPanel();
        fragment.setImage(image);
        fragment.setMessage(message);
        fragment.mListener = listener;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_story_panel, container, false);

        panelText = (TypeWriter) root.findViewById(R.id.panel_text);
        panelImage = (ImageView) root.findViewById(R.id.panel_image);

        panelImage.setImageBitmap(image);
        panelText.setCharacterDelay(75);
        panelText.listener = this;
        panelText.animateText(message);
        startTime = SystemClock.elapsedRealtime();
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void finishedTyping(){
        mListener.finished();
    }

    //setters/getters
    public interface Listener {

        void finished();

    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setImage(Bitmap image) {

        if (image == null) {
            Bitmap blank =  Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(blank);
            Paint paint = new Paint();
            paint.setColor(Color.TRANSPARENT);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPaint(paint);
            this.image = blank;
        } else {
            this.image = image;
        }
    }

    public void setPanelImage(ImageView panelImage) {
        this.panelImage = panelImage;
    }

    public void setPanelText(TypeWriter panelText) {
        this.panelText = panelText;
    }

}
