/**
 * 
 */
package br.com.marcosmilitao.idativosandroid.helper;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.marcosmilitao.idativosandroid.MainActivity;
import de.greenrobot.event.EventBus;


/**
 * @author lgnhm_000
 */
public class AccessUI extends Fragment {
	
	private static final String TAG = "access";
	protected static final int MAX_LEN_PASSWD = 8;

	public static class MaskEvent {

	}

	public static class AccessEvent {

	}

	public static class StatusChangeEvent {

	}

	public static class EpcSelectedEvent {
		Epc epc;

		public EpcSelectedEvent(Epc epc) {
			this.epc = epc;
		}

		public Epc getEpc() {
			return epc;
		}

		@Override
		public String toString() {
			return "EpcSelectedEvent [epc=" + epc + "]";
		}
	}

	ImageView statusOnImageView, statusTxImageView, statusRxImageView;
	ImageView detectStatusImageView, statusImageView;
	Button accessButton, maskButton;
	RadioGroup radioGroup;
	static EditText dataEditText, passwdEditText, epcEditText, addrEditText,
			lenEditText;
	static Spinner epcSpinner;
	static CheckBox dataCheckBox;
	static Epc epcToBeAccess;
	 
	RadioButton radio_read, radio_write;
	//RadioButton radio_lock,radio_kill;
	
	//other widgets
	TextView textViewPasswd,textViewData,textViewLocationTitle,textViewDetect,
	textViewStatus;

	Status on = Status.ON, tx = Status.BAD, rx = Status.BAD, detect = Status.BAD,
			status = Status.BAD;

	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
		EventBus.getDefault().register(this);
		/** 当有检查到有设备并且设备已经连接上，则第一个圆点由红色变为绿色 */
		if (MainActivity.currentDevice !=null && MainActivity.currentDevice.isConnected())
			setOn(Status.ON); // 绿
		else
			setOn(Status.BAD); // 红
		
		EpcSelectedEvent epcSelectedEvent = (EpcSelectedEvent) EventBus
				.getDefault().getStickyEvent(EpcSelectedEvent.class);

		if (epcSelectedEvent != null) {
			Log.i(TAG, "epc selected " + epcSelectedEvent);
			epcToBeAccess = epcSelectedEvent.getEpc();
			EventBus.getDefault().removeStickyEvent(epcSelectedEvent);
			epcEditText.setText(epcToBeAccess.getId());
		}
	}

	public static AccessUI me;
	
//	private InputFilter passwdFilter = new InputFilter() {
//		@Override
//		public CharSequence filter(CharSequence source, int start, int end,
//				Spanned dest, int dstart, int dend) {
//			return null;
//		}
//	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		me = this;
		View view = inflater.inflate(null, null);


		accessButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new AccessEvent());
			}
		});

		/*maskButton = (Button) view.findViewById(R.id.btn_mask);
		maskButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new MaskEvent());
			}
		});*/
		
		


		dataEditText.addTextChangedListener(new TextWatcher() {
			private int selectionStart ;
            private int selectionEnd ;
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			
				selectionStart = dataEditText.getSelectionStart();
                selectionEnd = dataEditText.getSelectionEnd();
                
            	if (s.length() == 0)
					return;
                
				if (!Utility.isHexString(s.toString())) {
					updateText(s);

					return;
				}
			}
			
			private void updateText(Editable s) {
				s.delete(selectionStart-1, selectionEnd);
                int tempSelection = selectionStart;
                dataEditText.setText(s);
                dataEditText.setSelection(tempSelection);
			}
		});
		

		passwdEditText.addTextChangedListener(new TextWatcher() {
			private int selectionStart ;
            private int selectionEnd ;
			private CharSequence temp;
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				Log.i(TAG, "onTextChanged " + s + " "+start + " "+ count+ this);				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
//				Log.i(TAG, "beforeTextChanged " + s + " "+start + " "+ count + this);
				temp = s;
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 0)
					return;
				selectionStart = passwdEditText.getSelectionStart();
                selectionEnd = passwdEditText.getSelectionEnd();
                
				if (!Utility.isHexString(s.toString())) {
					updateText(s);

					return;
				}
				
				if (s.length() > MAX_LEN_PASSWD) {
					updateText(s);
					return;
				}
			}
			
			private void updateText(Editable s) {
				s.delete(selectionStart-1, selectionEnd);
                int tempSelection = selectionStart;
                passwdEditText.setText(s);
                passwdEditText.setSelection(tempSelection);
			}
		});

		updateLang();

		return view;
	}

	private void freshStatus() {
		Map<ImageView, Status> map = new HashMap<ImageView, Status>();
		map.put(statusOnImageView, on);
		map.put(statusTxImageView, tx);
		map.put(statusRxImageView, rx);
		map.put(detectStatusImageView, detect);
		map.put(statusImageView, status);

		for (ImageView imageView : map.keySet()) {
			Status status = map.get(imageView);
			switch (status) {
			case ON:

				break;
//			case OFF:
//				imageView.setImageResource(R.drawable.ic_off);
//				break;
			case BAD:

				break;
			case INCOMPLETE:

				break;
			default:
				break;
			}
		}
	}

	public Status getDetect() {
		return detect;
	}

	public void setDetect(Status detect) {
		this.detect = detect;
		EventBus.getDefault().post(new StatusChangeEvent());
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
		EventBus.getDefault().post(new StatusChangeEvent());
	}

	public Status getOn() {
		return on;
	}

	public void setOn(Status on) {
		this.on = on;
		EventBus.getDefault().post(new StatusChangeEvent());
	}

	public Status getTx() {
		return tx;
	}

	public void setTx(Status tx) {
		this.tx = tx;
		EventBus.getDefault().post(new StatusChangeEvent());
	}

	public Status getRx() {
		return rx;
	}

	public void setRx(Status rx) {
		this.rx = rx;
		EventBus.getDefault().post(new StatusChangeEvent());
	}

	/**
	 * when status changed
	 * 
	 * @param e
	 */
	public void onEventMainThread(StatusChangeEvent e) {
		freshStatus();
	}

	public void onEventBackgroundThread(AccessEvent e) {
		int id = radioGroup.getCheckedRadioButtonId();
		
		if (id == radio_read.getId()) {
			readData();
		}
		/*else if (id == radio_kill.getId()) {
			killEpc();
		} */
		else if (id == radio_write.getId())
		{
			writeData();
		} 
		/*else {
			lockEpc();
		}*/
	}



	

	public void onEventBackgroundThread(MaskEvent e) {
		// TODO do mask
		if (MainActivity.currentDevice != null) {
			//				LinkUi.currentDevice.mask();
			Utility.showDialogInNonUIThread(getActivity(), "Warning", "未实现");
		} else {
			Utility.showDialogInNonUIThread(getActivity(), "Warning",
					"No devices connected!");
		}
	}
	
	public void onEventMainThread(ConfigUI.LangChanged e) {
		updateLang();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStop() {
		Log.i(TAG, "onStop");
		EventBus.getDefault().unregister(this);
		super.onStop();
	}

	@Override
	public void onPause() {
		Log.i(TAG, "onPause");
		EventBus.getDefault().unregister(this);
		super.onPause();
	}
	
	private void refreshSpinner() {


	}
	
	private void updateLang() {
//
	}

//	@Override
//	public void onCheckedChanged(RadioGroup group, int checkedId) {
//		switch(checkedId) {
//		case 0:
//			readData();
//			break;
//		case 1:
//			writeData();
//			break;
//		case 2:
//			lockEpc();
//			break;
//		case 4:
//			killEpc();
//			break;
//		default:
//			break;
//		}
//		
//	}

	private void killEpc() {
		if(!checkAccessKillEpc())
			return;
		
		String epc = epcToBeAccess.getId();
		String passwd = passwdEditText.getText().toString();
		
		try {
			MainActivity.currentDevice.KillTag(epc, passwd);
			byte[] ret = MainActivity.currentDevice.getCmdResultWithTimeout(3000);
			if (!VH73Device.checkSucc(ret)) {

			} else {

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
//			e.printStackTrace();

		}
	}
	
	private boolean checkAccessKillEpc() {
		if (epcToBeAccess == null || epcToBeAccess.getId().length() <= 0) {

			return false;
		}
		
		if (passwdEditText.getText().length() < 8) {

			return false;
		}
		return true;
	}

	private void eraseBlock() {
		if (!checkAccessEraseEnable())
			return;	
		String epc = epcToBeAccess.getId();
		int mem = epcSpinner.getSelectedItemPosition();
		int addr = Integer.valueOf(addrEditText.getText().toString());
		int len = Integer.valueOf(lenEditText.getText().toString());
		
		try {
			MainActivity.currentDevice.EraseBlock(epc, mem, addr, len);
			byte[] ret = MainActivity.currentDevice.getCmdResultWithTimeout(3000);
			if (!VH73Device.checkSucc(ret)) {

			} else {

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
//			e.printStackTrace();

		}
	}

	private boolean checkAccessEraseEnable() {
		if (epcToBeAccess == null || epcToBeAccess.getId().length() <= 0) {

			return false;
		}
		
		if (passwdEditText.getText().length() < 8) {

			return false;
		}
		
		if (addrEditText.getText().length() <= 0 || !Utility.isHexString(addrEditText.getText().toString()) ) {

			return false;
		}
		
		if (lenEditText.getText().length() <= 0|| !Utility.isHexString(lenEditText.getText().toString())) {

			return false;
		}
		
		return true;
	}

	private void lockEpc() {
		if (!checkAccessLock())
			return;
		String epc = epcToBeAccess.getId();
		String passwd = passwdEditText.getText().toString();
		
		try {
			MainActivity.currentDevice.BlockLock(epc, passwd);
			byte[] ret = MainActivity.currentDevice.getCmdResultWithTimeout(3000);
			if (!VH73Device.checkSucc(ret)) {

			} else {

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
//			e.printStackTrace();

		}
	}

	private boolean checkAccessLock() {
		if (epcToBeAccess == null || epcToBeAccess.getId().length() <= 0) {

			return false;
		}
		
		if (passwdEditText.getText().length() < 8) {

			return false;
		}
		return true;
	}

	private void writeData() {
		if (!checkAccessWriteEnable())
			return;
		
		int mem,addr,lenData;
		String passwd = "00000000";
		String data;
		
		mem = epcSpinner.getSelectedItemPosition();
		
		//len
		if (addrEditText.getText().length() > 0 && Utility.isHexString(addrEditText.getText().toString()))
			addr = Integer.valueOf(addrEditText.getText().toString());
		else
			addr = 0;
		
		if (passwdEditText.getText().length() != 0) {
			passwd = passwdEditText.getText().toString();
		}
		
		data = dataEditText.getText().toString();
		
		try {
			MainActivity.currentDevice.WriteWordBlock(epcToBeAccess.getId(), mem, addr, data, passwd);
			byte[] ret = MainActivity.currentDevice.getCmdResultWithTimeout(3000);
			if (!VH73Device.checkSucc(ret)) {

			} else {

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
//			e.printStackTrace();

		}
	}

	private boolean checkAccessWriteEnable() {
		if (epcToBeAccess == null || epcToBeAccess.getId().length() <= 0) {

			return false;
		}
		
		if (dataEditText.getText().length() <= 0) {

			return false;
		}

		return true;
	}

	//ReadWordBlock
	private void readData() {		
		if (!checkAccessReadEnable()) {
			return;
		}
		int mem,addr,lenData;
		String passwd = "00000000";
		
		if (epcToBeAccess==null || epcToBeAccess.getId().length() <= 0)
			return;
		mem = epcSpinner.getSelectedItemPosition();
		
		//len
		if (addrEditText.getText().length() > 0 && Utility.isHexString(addrEditText.getText().toString()))
			addr = Integer.valueOf(addrEditText.getText().toString());
		else
			addr = 0;
		// addr
		if (lenEditText.getText().length() > 0 && Utility.isHexString(lenEditText.getText().toString()))
			lenData = Integer.valueOf(lenEditText.getText().toString());
		else 
			lenData = 0;
		
		if (passwdEditText.getText().length() != 0) {
			passwd = passwdEditText.getText().toString();
		}
		
		try {
			MainActivity.currentDevice.ReadWordBlock(epcToBeAccess.getId(), mem, addr, lenData, passwd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//read
		try {
			byte[] ret = MainActivity.currentDevice.getCmdResultWithTimeout(3000);
			
			StringBuilder builder = new StringBuilder(Utility.bytes2HexString(ret));
			builder.delete(0, 6);
			builder.delete(builder.length()-2, builder.length());			
			

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
//			e.printStackTrace();

		}
	}
	
	/**
	 * check data length ......
	 * @return
	 */
	private boolean checkAccessReadEnable() {
		if (epcToBeAccess == null || epcToBeAccess.getId().length() <= 0) {

			return false;
		}
		
		if (passwdEditText.getText().length() < 8) {

			return false;
		}
		
		return true;
	}
	
	
	public void onEventMainThread(VH73Device.GetCommandResultSuccess e) {
		if (e.isSuccess())
			setRxWithBlink(ConfigUI.cmd_timeout, Status.ON, Status.BAD);
		else
			setRx(Status.BAD);
	}
	
	public void onEventMainThread(VH73Device.SendCommandSuccess e) {
		if (e.isSuccess()) {
			setTxWithBlink(ConfigUI.cmd_timeout, Status.ON, Status.BAD);
		}
		else {
			setTx(Status.BAD);
		}
	}
	
	/**
	 * tx blink 
	 * @param timeout
	 * @param from
	 * @param to
	 */
	private void setTxWithBlink(final long timeout, Status from, final Status to) {
		setTx(from);
		new Thread() {
			public void run() {
				try {
					sleep(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Thread() {
					public void run() {
						Looper.prepare();
						setTx(to);
					}
				}.start();
			}
		}.start();
	}
	
	private void setRxWithBlink(final long timeout, Status from, final Status to) {
		setRx(from);
		new Thread() {
			public void run() {
				try {
					sleep(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Thread() {
					public void run() {
						Looper.prepare();
						setRx(to);
					}
				}.start();
			}
		}.start();
	}
}
