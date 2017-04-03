package com.gonigoni.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.gonigoni.babysharksafeway.R;
import com.gonigoni.glrobal.Session;
import com.gonigoni.ui.ActivityType;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class MainActivity extends Activity implements MainView {


    @BindView(R.id.et_start_lot)
    EditText etStartLot;
    @BindView(R.id.et_end_lot)
    EditText etEndLot;
    @BindView(R.id.startLotImagebtn)
    ImageView startLotImageBtn;
    @BindView(R.id.endLotImageBtn)
    ImageView endLotImageBtn;
    @BindView(R.id.settingBtn)
    ImageView settingBtn;
    @BindView(R.id.mainLayoutId)
    FrameLayout mainFrameLayout;

    private final MainPresenter mainPresenter = new MainPresenter();
    private TMapView tmapview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);

        requestRecordAudioPermission();
        requestSmsPermission();

        mainPresenter.attachView(this);
    }

    @OnClick(R.id.startLotImagebtn)
    public void clickStartLotBtn(ImageView btn) {
        final String startLotInput = etStartLot.getText().toString();
        mainPresenter.inputDestination(startLotInput, ActivityType.START_LOT_ACT);
        downKeyboard(getBaseContext(), etStartLot);
    }

    @OnClick(R.id.endLotImageBtn)
    public void clickEndLotBtn(ImageView btn) {
        final String endLotInput = etEndLot.getText().toString();
        mainPresenter.inputDestination(endLotInput, ActivityType.END_LOT_ACT);
        downKeyboard(getBaseContext(), etEndLot);
    }

    @OnClick(R.id.settingBtn)
    public void clickSettingBtn(ImageView btn) {
        mainPresenter.clickSettingBtn();
    }

    @Override
    public void initTMapView() {
        tmapview = new TMapView(getBaseContext());

        final String mapApiKey = "d82d65c6-af36-3225-89bb-1f0a995711af";
        tmapview.setSKPMapApiKey(mapApiKey);
        tmapview.clearFocus();
        final String startLocationName = "금천구";
        Location startLocation = new Location(startLocationName);
        // 금천구 좌표값
        startLocation.setLatitude(37.456876);
        startLocation.setLongitude(126.895446);
        // 시작 위치
        tmapview.setCenterPoint(startLocation.getLongitude(), startLocation.getLatitude());
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setIconVisibility(true);
        tmapview.setZoomLevel(50);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setCompassMode(false);
        tmapview.setTrackingMode(false);
        tmapview.removeAllMarkerItem();
        mainFrameLayout.addView(tmapview);
    }

    @Override
    public void showWrongInputToast() {
        Toast.makeText(this, getString(R.string.wrong_input), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    public void startActivityForType(Class activity, String query, int type) {
        final Session session = Session.getInstanse();
        switch (type) {
            case ActivityType.START_LOT_ACT : {
                session.setFlag(false);
                session.setStartQuery(query);
            } break;
            case ActivityType.END_LOT_ACT : {
                session.setFlag(true);
                session.setEndQuery(query);
            } break;
        }
        startActivityForResult(new Intent(this, activity), type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Session session = Session.getInstanse();
        switch (resultCode) {
            case ActivityType.START_LOT_ACT: {
                etStartLot.setText(session.getStartPOIItem().getPOIName());
            } break;
            case ActivityType.END_LOT_ACT: {
                etEndLot.setText(session.getEndPOIItem().getPOIName());
                // 이제 지도에 보여주기
                setTmap();
            } break;
        }
    }

    private void setTmap() {
        TMapData tMapData = new TMapData();
        final Session session = Session.getInstanse();
        final TMapPoint startPoint = session.getStartPOIItem().getPOIPoint();
        final TMapPoint endPoint = session.getEndPOIItem().getPOIPoint();
        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, startPoint, endPoint,
                tMapPolyLine -> {
                    DashPathEffect dashPath = new DashPathEffect(new float[]{20,10}, 0);
                    tMapPolyLine.setOutLinePathEffect(dashPath);
                    tMapPolyLine.setLineWidth(30.0f);
                    tMapPolyLine.setLineColor(Color.parseColor("#903aa8"));
                    tmapview.setCenterPoint(startPoint.getLongitude(), startPoint.getLatitude());
                    tmapview.addTMapPath(tMapPolyLine);
                    tmapview.setZoomLevel(14);
                } );
    }

    public void downKeyboard(Context context, EditText editText) {
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void requestRecordAudioPermission() {
        //check API version, do nothing if API version < 23!
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion > Build.VERSION_CODES.LOLLIPOP) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                }
            }
        }
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("Activity", "Granted!");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("Activity", "Denied!");
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
