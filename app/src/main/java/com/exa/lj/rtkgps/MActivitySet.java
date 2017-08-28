package com.exa.lj.rtkgps;

/**
 * Created by Administrator on 2017-6-9.
 */

//public class MActivitySet extends AppCompatActivity {
//    private EditText rawobsdata;
//    private EditText rawnavdata;
//    private EditText rawssrdata;
//
//    private Button myOK;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.setlayout);
//
//        rawobsdata = (EditText)findViewById(R.id.rawobsdata);
//        rawnavdata = (EditText)findViewById(R.id.rawnavdata);
//        rawssrdata = (EditText)findViewById(R.id.rawssrdata);
//        myOK = (Button)findViewById(R.id.OK);
//        myOK.setOnClickListener(new myOKListener());
//    }
//    class myOKListener implements View.OnClickListener{
//
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent();
//            String strobsRaw     = rawobsdata.getText().toString();
//            String strnavRaw     = rawnavdata.getText().toString();
//            String strssrRaw     = rawssrdata.getText().toString();
//
//            StaticVal.strobspath = strobsRaw;
//            StaticVal.strnavpath = strnavRaw;
//            StaticVal.strssrpath = strssrRaw;
//
//            finish();
//        }
//    }
//
//}
