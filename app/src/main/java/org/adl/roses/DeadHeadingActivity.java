package org.adl.roses;

import android.os.Bundle;

public class DeadHeadingActivity extends ContentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(getString(R.string.mod_deadheading_name));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead_heading);
        mOnCreate(savedInstanceState);
    }
}
