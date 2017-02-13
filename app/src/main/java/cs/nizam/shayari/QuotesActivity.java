package cs.nizam.shayari;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import cs.nizam.shayari.database.MyDatabase;
import cs.nizam.shayari.listener.OnErrorListener;
import cs.nizam.shayari.listener.OnSuccessListener;
import cs.nizam.shayari.model.Messages;

public class QuotesActivity extends AppCompatActivity {

    private static final String CATEGORY = "cat";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private List<Messages> fullMessageList;
    private int categoryId;
    private List<Messages> filteredMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        categoryId = getIntent().getIntExtra(CATEGORY,2);
        fullMessageList = new ArrayList<>();
        filteredMessages = new ArrayList<>();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), filteredMessages);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        getMessages();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quotes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void startNewActivity(Context context, int category) {
        Intent intent = new Intent(context, QuotesActivity.class);
        intent.putExtra(CATEGORY, category);
        context.startActivity(intent);
    }

    private void getMessages() {
        MyDatabase.getInstance().getMessages(new OnSuccessListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (int i=1; i<=dataSnapshot.getChildrenCount(); i++) {
                    fullMessageList.add(dataSnapshot.child(""+i).getValue(Messages.class));
                }
                filteredMessages = filterMessages();
                mSectionsPagerAdapter.notifyDataSetChanged();
            }
        }, new OnErrorListener() {
            @Override
            public void onError(DatabaseError databaseError) {

            }
        });
    }
    private List<Messages> filterMessages(){

        List<Messages> messages = new ArrayList<Messages>();
        for (int i = 0; i < fullMessageList.size(); i++) {
            Messages message = fullMessageList.get(i);
            if (message != null && message.getCat() == categoryId) {
                messages.add(message);
            }
        }
        return messages;
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_QUOTE = "Quote";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Messages message) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_QUOTE, message.getMsg());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quotes, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getArguments().getString(ARG_QUOTE));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Messages> messages;

        public SectionsPagerAdapter(FragmentManager fm, List<Messages> filteredMessages) {
            super(fm);
            this.messages = filteredMessages;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, messages.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return messages.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Quotes";
        }
    }
}
