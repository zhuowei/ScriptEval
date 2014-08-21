package net.zhuoweizhang.scripteval;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ConsoleFragment extends Fragment {
	private TextView mainText;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		((MainActivity) getActivity()).consoleFragment = this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.console_fragment, container, false);
		mainText = (TextView) view.findViewById(R.id.console_fragment_output_text);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		mainText.setText(((MainActivity) getActivity()).printBuffer.toString());
	}

	public void updateText(StringBuilder sb) {
		if (mainText == null) return;
		mainText.setText(sb.toString());
	}

}