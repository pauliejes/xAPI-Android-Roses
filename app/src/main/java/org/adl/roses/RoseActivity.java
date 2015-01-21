package org.adl.roses;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Context;
import gov.adlnet.xapi.model.Verb;
import gov.adlnet.xapi.model.Verbs;

public class RoseActivity extends ContentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(getString(R.string.mod_what_name));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rose);

        // Set the module ID and current slide
        setAndroidId(getIntent().getExtras().getInt("requestCode"));
        setCurrentSlide(getIntent().getExtras().getInt("slideId"));

        // Set or generate the attempt ID
        String attemptId = getIntent().getExtras().getString("attemptId", null);
        if (attemptId == null){
            generateAttempt();
        }
        else{
            setCurrentAttempt(attemptId);
        }


        // Get actor and send initialized statement and first slide statement
        Agent actor = getActor();
        Activity init_act = createActivity(getString(R.string.app_activity_iri) + getString(R.string.mod_what_path)
                        +"?attemptId=" + getCurrentAttempt(), getString(R.string.mod_what_name),
                        getString(R.string.mod_what_description));
        Context init_con = createContext(null, null, null, null, true);

//        Activity slide_act = createActivity(getString(R.string.app_activity_iri) + getString(R.string.mod_what_path) + "#" +
//                        getCurrentSlide() +"?attemptId=" + getCurrentAttempt(),
//                        getString(R.string.mod_what_name) + " - Slide " + (getCurrentSlide() + 1),
//                        getString(R.string.mod_what_description) + " - Slide " + (getCurrentSlide() + 1));
//        Context what_con = createContext(getString(R.string.mod_what_path), getCurrentAttempt(),
//                        getString(R.string.mod_what_name), getString(R.string.mod_what_description), false);

        // send initialize statement
        MyStatementParams what_init_params = new MyStatementParams(actor, Verbs.initialized(), init_act, init_con);
        WriteStatementTask what_init_stmt_task = new WriteStatementTask();
        what_init_stmt_task.execute(what_init_params);

        // send read statement
//        HashMap<String, String> verb_lang = new HashMap<String, String>();
//        verb_lang.put("en-US", "read");
//        Verb verb = new Verb("http://example.com/verbs/read", verb_lang);
//        MyStatementParams what_slide_params = new MyStatementParams(actor, verb, slide_act, what_con);
//        WriteStatementTask what_slide_stmt_task = new WriteStatementTask();
//        what_slide_stmt_task.execute(what_slide_params);


        // Set onClick listeners
        Button button = (Button) findViewById(R.id.whatSuspend);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                suspendActivity();
            }
        });

        Button pbutton = (Button) findViewById(R.id.whatPrev);
        pbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                previousSlide();
            }
        });

        Button nbutton = (Button) findViewById(R.id.whatNext);
        nbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                nextSlide();
            }
        });


        // Check that the activity is u sing he layout version with
        // the fragment_container FameLayout
        if (findViewById(R.id.textFrag) != null){

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null){
                return;
            }

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SlideFragment frag = new SlideFragment();
            fragmentTransaction.add(R.id.textFrag, frag).commit();
        }
        //Set modeule just opened
//        setJustOpened(true);
    }
}
