package net.zhuoweizhang.scripteval;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class EditFragment extends Fragment {

	private TextView mainText;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.edit_fragment, container, false);
		mainText = (TextView) v.findViewById(R.id.edit_fragment_code_text);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.edit_menu, menu);
	}

	@Override
	public void onResume() {
		super.onResume();
		String code = getActivity().getSharedPreferences("prefs", 0).getString("code", "");
		mainText.setText(code);
	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().getSharedPreferences("prefs", 0).edit().putString("code", mainText.getText().toString()).apply();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_run:
				((MainActivity) getActivity()).runScript(mainText.getText().toString());
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}