package io.karte.tracker_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import io.karte.android.tracker.Tracker;

public class MainActivity extends AppCompatActivity {
  public static final String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));
    getSupportActionBar().setTitle("SampleApplication");
    setEventListeners();
  }


  private void setEventListeners() {
    final Button buttonIdentifyEvent = findViewById(R.id.send_identify_event);
    buttonIdentifyEvent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendIdentifyEvent();
      }
    });

    final Button buttonViewEvent = findViewById(R.id.send_view_event);
    buttonViewEvent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendViewEvent();
      }
    });

    final Button buttonBuyEvent = findViewById(R.id.send_buy_event);
    buttonBuyEvent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendBuyEvent();
      }
    });

  }

  private void sendIdentifyEvent() {

    final EditText editText = findViewById(R.id.edit_user_id);
    final String user_id = editText.getText().toString();
    if (user_id.length() > 0) {
      try {
        JSONObject values = new JSONObject();
        values.put("user_id", user_id);
        values.put("is_app_user", true);
        Tracker.getInstance(this, SampleApp.APP_KEY).identify(values);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    } else {
      Log.w(TAG, "no user_id");
    }
  }

  private void sendViewEvent() {
    Log.i(TAG, "view event button clicked");
    try {
      String viewName = ((EditText) findViewById(R.id.view_name_edit)).getText().toString();
      JSONObject values = new JSONObject("{\"title\":\"" + viewName + "\"}");
      Tracker.getInstance(this, SampleApp.APP_KEY).view(viewName, values);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void sendBuyEvent() {
    Log.i(TAG, "buy event button clicked");
    try {
      JSONObject values = new JSONObject(
        "{" +
          "\"affiliation\":\"shop name\"," +
          "\"revenue\":" + String.valueOf((int) (Math.random() * 10000)) + "," +
          "\"shipping\":100," +
          "\"tax\":10," +
          "\"items\":[{" +
          "  \"item_id\":\"test\"," +
          "  \"name\":\"掃除機A\"," +
          "  \"category\": [\"家電\", \"掃除機\"]," +
          "  \"price\":" + String.valueOf((int) (Math.random() * 1000)) + "," +
          "  \"quantity\":1" +
          "}]" +
          "}"
      );
      Tracker.getInstance(this, SampleApp.APP_KEY).track("buy", values);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    Tracker.getInstance(this,SampleApp.APP_KEY).view("main");
  }
}
