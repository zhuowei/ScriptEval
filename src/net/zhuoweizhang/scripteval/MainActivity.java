package net.zhuoweizhang.scripteval;

import android.app.*;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends Activity implements ScriptManager.ConsoleListener
{
	private ViewPager viewPager;
	private TabsAdapter tabsAdapter;
	public StringBuilder printBuffer = new StringBuilder();
	public ConsoleFragment consoleFragment;
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		viewPager = new ViewPager(this);
		viewPager.setId(R.id.viewpager);
		setContentView(viewPager);
		ActionBar ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		tabsAdapter = new TabsAdapter(this, viewPager);
		tabsAdapter.addTab(ab.newTab().setText(R.string.tab_edit),
			EditFragment.class, null);
		tabsAdapter.addTab(ab.newTab().setText(R.string.tab_console),
			ConsoleFragment.class, null);
	}

	public void runScript(String s) {
		// this is ugly don't copy plz
		viewPager.setCurrentItem(1);
		ScriptManager.listener = this;
		ScriptManager.runScript(s);
		ScriptManager.listener = null;
	}

	public void print(String s) {
		// this is also ugly don't copy plz
		printBuffer.append(s).append('\n');
		if (consoleFragment != null) consoleFragment.updateText(printBuffer);
	}

}
