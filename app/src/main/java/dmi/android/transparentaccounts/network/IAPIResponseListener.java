package dmi.android.transparentaccounts.network;

import java.util.ArrayList;

/**
 * Created by DZCDMAY on 15.9.2016.
 */
public interface IAPIResponseListener {

    public void onListGotSuccessfully(ArrayList<?> data);

    public void onObjectGotSuccessfully(Object data);

    public void onErrorGot(final APIError error);
}
