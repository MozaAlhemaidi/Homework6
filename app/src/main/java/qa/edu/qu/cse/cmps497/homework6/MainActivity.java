package qa.edu.qu.cse.cmps497.homework6;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    static final String TAG = "MainActivity";
    static final String http = "http://www.devasque.com/cmps497/homeworks.json";

    TextView mTextView;
    ProgressBar mProgressBar;
    ListView mListView;
    ArrayList<Homework> homewworkList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: grab references to your activity's views
        mTextView=(TextView)findViewById(R.id.textview_wait);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mListView=(ListView)findViewById(R.id.listView);
        homewworkList=new ArrayList<Homework>();
        //TODO: launch the AsyncTask to populate the listview
        //TODO: with online content
        new HttpGetJSON().execute(http);

        //TODO: don't forget the listview's onItemClick listener
        //TODO: which will start new activities and push Extras() to them.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
                //intent.putExtra("http",http);
                //intent.putExtra("selectedHW",(Parcelable)homewworkList.get(position));
                Homework hw = homewworkList.get(position);
                intent.putExtra("title",hw.getTitle());
                intent.putExtra("summary",hw.getSummary());
                intent.putExtra("purpose",hw.getPurpose());
                intent.putExtra("Due",hw.getDue());
                intent.putExtra("is_screenshot",hw.getIs_screenshot());
                intent.putExtra("points",hw.getPoints());
                intent.putExtra("screenshot",hw.getScreenshot());
                startActivity(intent);

            }
        });
    }

    private class HttpGetJSON extends AsyncTask<String, Void, List<String>> {
        HttpURLConnection httpUrlConnection;
        private String TAG = "HttpGetJASON";

        @Override
        protected void onPreExecute() {
            mTextView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
        @Override
        protected List<String> doInBackground(String... params) {
            String data=null;
            try{
                Thread.sleep(2000);
                httpUrlConnection = (HttpURLConnection) new URL(params[0])
                        .openConnection();
                InputStream in = new BufferedInputStream(
                        httpUrlConnection.getInputStream());

                data = readStream(in);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (data != null) {
                return getJSON(data);
            }
            return null;
        }
        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer data = new StringBuffer("");
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return data.toString();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            if(strings == null){
                //nothing happen
                Log.e(TAG,"error in onPostExecute in HttpGetJSON ");
            }
            else{
                mTextView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                mListView.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,strings));
            }
        }
        private List<String> getJSON(String data) {
            final String HOMEWORKS_TAG="homeworks";
            final String TITLE_TAG="title";
            final String SUMMARY_TAG="summary";
            final String PURPOSE_TAG="purpose";
            final String DUE_TAG="due";
            final String IS_SCREENSHOT_TAG="is_screenshot";
            final String SCREENSHOT_TAG="screenshot";
            final String POINTS_TAG="points";


            List<String> result = new ArrayList<String>();

            try {

                // Get top-level JSON Object - a Map
                JSONObject responseObject = (JSONObject) new JSONTokener(
                        data).nextValue();

                // Extract value of "earthquakes" key -- a List
                JSONArray homeworks = responseObject
                        .getJSONArray(HOMEWORKS_TAG);

                // Iterate over homeworks list
                for (int idx = 0; idx < homeworks.length(); idx++) {

                    // Get single homework data - a Map
                    JSONObject homework = (JSONObject) homeworks.get(idx);

                    // Summarize homework data as a string and add the title  to
                    // result
                    String title=""+homework.get(TITLE_TAG);
                    String summary=""+homework.get(SUMMARY_TAG);;
                    String purpose=""+homework.get(PURPOSE_TAG);;
                    String due=""+homework.get(DUE_TAG);;
                    boolean is_screenshot=(boolean)homework.get(IS_SCREENSHOT_TAG);
                    String screenshot = (is_screenshot)? ""+homework.get(SCREENSHOT_TAG):null;
                    int points=(int)homework.get(POINTS_TAG);
                    homewworkList.add(new Homework(title,summary,purpose,due,is_screenshot,screenshot,points));
                    result.add(title);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
