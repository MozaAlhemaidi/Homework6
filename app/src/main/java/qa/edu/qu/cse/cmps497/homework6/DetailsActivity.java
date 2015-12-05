package qa.edu.qu.cse.cmps497.homework6;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {

    TextView mTextViewTitle, mTextViewSummary, mTextViewPoints, mTextViewPurpose, mTextViewDue;
    TextView mTextViewPleaseWait;
    ImageView mImageView;
    ProgressBar mProgressBar;

    final static String TAG = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //TODO: grab references to your views
        //TODO: grab the extras you pushed and populate the views accordingly
        mTextViewTitle=(TextView)findViewById(R.id.title);
        mTextViewSummary=(TextView)findViewById(R.id.summary);
        mTextViewPoints=(TextView)findViewById(R.id.points);
        mTextViewPurpose=(TextView)findViewById(R.id.purpose);
        mTextViewDue=(TextView)findViewById(R.id.due);
        mTextViewPleaseWait=(TextView)findViewById(R.id.textview_wait);
        mImageView=(ImageView)findViewById(R.id.imageView);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        Intent intent=getIntent();
        mTextViewTitle.setText(intent.getStringExtra("title"));
        mTextViewSummary.setText(intent.getStringExtra("summary"));
        mTextViewPoints.setText(intent.getIntExtra("points",0));
        mTextViewPurpose.setText(intent.getStringExtra("purpose"));
        mTextViewDue.setText(intent.getStringExtra("Due"));
        boolean is_screenshot = (boolean)intent.getBooleanExtra("is_screenshot", false);

        //TODO: if item has an image. Launch an asynctask to grab that image using its URL
        //TODO: otherwise, just put text "no screenshot available"
        //TODO: play with the views' Visibility to make them appear/disappear dynamically
        if(is_screenshot){
            new HttpGetIMGE().execute(intent.getStringExtra("screenshot"));
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mTextViewPleaseWait.setText("no screenshot available");

        }
    }



    //TODO: Your AsyncTask must retrieve the screenshot from the URL field
    //TODO: in your Model. It then needs to set the image to the ImageView of your activity

    //TODO: tip. After you open a HttpURLConnection and get an InputStream. Pass that InputStream
    //TODO: to:
    //BitmapFactory.decodeStream(in)
    //TODO: to get a Bitmap java object which you can set to your ImageView by calling
    //mImageView.setImageBitmap()

    private class HttpGetIMGE extends AsyncTask<String, Void, Bitmap> {
        HttpURLConnection httpUrlConnection;
        private String TAG = "HttpGetJASON";

        @Override
        protected void onPreExecute() {
            mTextViewPleaseWait.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap=null;
            try{
                Thread.sleep(2000);
                httpUrlConnection = (HttpURLConnection) new URL(params[0])
                        .openConnection();
                InputStream in = new BufferedInputStream(
                        httpUrlConnection.getInputStream());
                bitmap=BitmapFactory.decodeStream(in);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                return bitmap;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap == null){
                //nothing happen
                Log.e(TAG,"error in onPostExecute in HttpGetJSON ");
            }
            else{
                mTextViewPleaseWait.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageBitmap(bitmap);
            }
        }

    }
}
