package br.com.marcosmilitao.idativosandroid.helper;

import android.bluetooth.BluetoothAdapter;

public class BluetoothHelper {
	public static boolean supportBluetooth() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			return false;
		}
		return true;
	}
}
