package dmi.android.transparentaccounts.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import dmi.android.transparentaccounts.object.AccountTeaser;
import dmi.android.transparentaccounts.object.Amount;
import dmi.android.transparentaccounts.object.Sender;
import dmi.android.transparentaccounts.object.TransactionTeaser;

/**
 * Created by DZCDMAY on 15.9.2016.
 */

/**
 * Provides access to network operations with API
 */
public class APIManager {

    public static APIManager instance = new APIManager();

    public static APIManager getInstance() {
        return instance;
    }

    private String apiKey = "221bfc91-3bd0-4f86-bf71-f47345c590c6";
    private final String TAG = "Transparent." + this.getClass().getName();

    private ArrayList<AccountTeaser> accountTeasers = new ArrayList<AccountTeaser>();
    private ArrayList<TransactionTeaser> transactionTeasers = new ArrayList<TransactionTeaser>();
    private AccountTeaser accountTeaser;

    /**
     * Get all Accounts and call return method on IAPIResponseListener
     *
     * @param listener - listener that receive response data
     */
    public void getTransparentAccountsList(IAPIResponseListener listener){
        new GetListAsyncTask().execute(listener);
    }

    /**
     * Get account details
     * @param listener
     * @param id
     */
    public void getTransparentAccountDetails(IAPIResponseListener listener, String id){
        new GetDetailsAsyncTask(id).execute(listener);
    }

    /**
     * Get Transactions of current account
     * @param listener
     * @param id
     */
    public void getTransparentAccountTransactions(IAPIResponseListener listener, String id){
        new GetTransactionsAsyncTask(id).execute(listener);
    }

    /**
     * AsyncTask to get Account accountTeasers
     */
    private class GetListAsyncTask extends AsyncTask<IAPIResponseListener, Integer, ArrayList<AccountTeaser>> {

        private IAPIResponseListener listener;

        @Override
        protected ArrayList<AccountTeaser> doInBackground(IAPIResponseListener... listeners) {
            accountTeasers.clear();
            this.listener = listeners[0];
            APIError error;
            try {
                URL url = new URL(APIUrl.transparentAccountsURL);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("WEB-API-key", apiKey);
                int responseCode = connection.getResponseCode();

                switch (responseCode) {
                    /* in case of OK */
                    case 200:
                        InputStream inputStr = connection.getInputStream();
                        String line;
                        StringBuilder sb = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStr, "UTF-8"), 8);
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        String result = sb.toString();

                        /**
                         * Fill array of Accounts parsing JSON
                         */
                        JSONObject mainObj = new JSONObject(result);
                        JSONArray arr = mainObj.getJSONArray("accounts");

                        for(int i = 0; i<arr.length(); i++){
                            JSONObject obj = arr.getJSONObject(i);
                            AccountTeaser teaser = new AccountTeaser();
                            teaser.setAccountNumber(obj.getString("accountNumber"));
                            teaser.setBankCode(obj.getString("bankCode"));
                            teaser.setTransparencyFrom(obj.getString("transparencyFrom"));
                            teaser.setTransparencyTo(obj.getString("transparencyTo"));
                            teaser.setIban(obj.getString("iban"));
                            teaser.setCurrency(obj.getString("currency"));
                            teaser.setPublicationTo(obj.getString("publicationTo"));
                            teaser.setBalance(obj.getDouble("balance"));
                            teaser.setActualizationDate(obj.getString("actualizationDate"));
                            accountTeasers.add(teaser);
                        }

                        break;
                    case 204:

                        break;
                    case 400:
                        error = new APIError("Logical error in parsing request", 400);
                        listener.onErrorGot(error);
                        break;
                    case 403:
                        error = new APIError("User not logged in, invalid token, etc.", 403);
                        listener.onErrorGot(error);
                        break;
                    case 404:
                        error = new APIError("This kind of resource does not exist", 404);
                        listener.onErrorGot(error);
                        break;
                    case 415:
                        error = new APIError("Unsupported media type (Accept/Accept-Language unsupported)", 415);
                        listener.onErrorGot(error);
                        break;
                    case 500:
                        error = new APIError("Internal error happened (a Java exception happened)", 500);
                        listener.onErrorGot(error);
                        break;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return accountTeasers;
        }

        @Override
        protected void onPostExecute(ArrayList<AccountTeaser> accountTeasers) {
            listener.onListGotSuccessfully(accountTeasers);
            super.onPostExecute(accountTeasers);
        }
    }

    /**
     * AsyncTask to get Account details
     */
    private class GetDetailsAsyncTask extends AsyncTask<IAPIResponseListener, Integer, AccountTeaser> {

        private IAPIResponseListener listener;
        private String id;

        public GetDetailsAsyncTask(String id){
             this.id = id;
         }

        @Override
        protected AccountTeaser doInBackground(IAPIResponseListener... listeners) {
            accountTeaser = new AccountTeaser();
            this.listener = listeners[0];
            APIError error;
            try {
                URL url = new URL(APIUrl.transparentAccountsURL + "/" + id);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("WEB-API-key", apiKey);
                int responseCode = connection.getResponseCode();

                switch (responseCode) {
                    /* in case of OK */
                    case 200:
                        InputStream inputStr = connection.getInputStream();
                        String line;
                        StringBuilder sb = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStr, "UTF-8"), 8);
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        String result = sb.toString();

                        /**
                         * Fill details Account parsing JSON
                         */
                        JSONObject obj = new JSONObject(result);
                        accountTeaser.setAccountNumber(obj.getString("accountNumber"));
                        accountTeaser.setBankCode(obj.getString("bankCode"));
                        accountTeaser.setTransparencyFrom(obj.getString("transparencyFrom"));
                        accountTeaser.setTransparencyTo(obj.getString("transparencyTo"));
                        accountTeaser.setIban(obj.getString("iban"));
                        accountTeaser.setCurrency(obj.getString("currency"));
                        accountTeaser.setPublicationTo(obj.getString("publicationTo"));
                        accountTeaser.setBalance(obj.getDouble("balance"));
                        accountTeaser.setActualizationDate(obj.getString("actualizationDate"));
                        break;
                    case 204:

                        break;
                    case 400:
                        error = new APIError("Logical error in parsing request", 400);
                        listener.onErrorGot(error);
                        break;
                    case 403:
                        error = new APIError("User not logged in, invalid token, etc.", 403);
                        listener.onErrorGot(error);
                        break;
                    case 404:
                        error = new APIError("This kind of resource does not exist", 404);
                        listener.onErrorGot(error);
                        break;
                    case 415:
                        error = new APIError("Unsupported media type (Accept/Accept-Language unsupported)", 415);
                        listener.onErrorGot(error);
                        break;
                    case 500:
                        error = new APIError("Internal error happened (a Java exception happened)", 500);
                        listener.onErrorGot(error);
                        break;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return accountTeaser;
        }

        @Override
        protected void onPostExecute(AccountTeaser accountTeaser) {
            listener.onObjectGotSuccessfully(accountTeaser);
            super.onPostExecute(accountTeaser);
        }
    }

    /**
     * AsyncTask to get Account accountTeasers
     */
    private class GetTransactionsAsyncTask extends AsyncTask<IAPIResponseListener, Integer, ArrayList<TransactionTeaser>> {

        private IAPIResponseListener listener;
        private String id;

        public GetTransactionsAsyncTask(String id){
            this.id = id;
        }

        @Override
        protected ArrayList<TransactionTeaser> doInBackground(IAPIResponseListener... listeners) {
            accountTeasers.clear();
            this.listener = listeners[0];
            APIError error;
            try {
                URL url = new URL(APIUrl.transparentAccountsURL + "/" + id + "/transactions/");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("WEB-API-key", apiKey);
                int responseCode = connection.getResponseCode();

                switch (responseCode) {
                    /* in case of OK */
                    case 200:
                        InputStream inputStr = connection.getInputStream();
                        String line;
                        StringBuilder sb = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStr, "UTF-8"), 8);
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        String result = sb.toString();

                        Log.d(TAG, result);
                        /**
                         * Fill array of Transactions parsing JSON
                         */
                        JSONObject mainObj = new JSONObject(result);
                        JSONArray arr = mainObj.getJSONArray("transactions");

                        for(int i = 0; i<arr.length(); i++){
                            JSONObject obj = arr.getJSONObject(i);
                            TransactionTeaser teaser = new TransactionTeaser();
                            Amount amount = new Amount();
                            Sender sender = new Sender();
                            JSONObject am = obj.getJSONObject("amount");
                            JSONObject se = obj.getJSONObject("sender");

                            teaser.setDueDate(obj.getString("dueDate"));
                            teaser.setProcessingDate(obj.getString("processingDate"));

                            amount.setValue(am.getDouble("value"));
                            amount.setCurrency(am.getString("currency"));
                            amount.setPrecision(am.getInt("precision"));

                            sender.setAccountNumber(se.getString("accountNumber"));
                            sender.setBankCode(se.getString("bankCode"));
                            sender.setConstantSymbol(se.getString("constantSymbol"));
                            sender.setIban(se.getString("iban"));
                            sender.setSpecificSymbol(se.getString("specificSymbol"));
                            sender.setSpecificSymbolParty(se.getString("specificSymbolParty"));

                            teaser.setSender(sender);
                            teaser.setAmount(amount);

                            transactionTeasers.add(teaser);
                        }

                        break;
                    case 204:

                        break;
                    case 400:
                        error = new APIError("Logical error in parsing request", 400);
                        listener.onErrorGot(error);
                        break;
                    case 403:
                        error = new APIError("User not logged in, invalid token, etc.", 403);
                        listener.onErrorGot(error);
                        break;
                    case 404:
                        error = new APIError("This kind of resource does not exist", 404);
                        listener.onErrorGot(error);
                        break;
                    case 415:
                        error = new APIError("Unsupported media type (Accept/Accept-Language unsupported)", 415);
                        listener.onErrorGot(error);
                        break;
                    case 500:
                        error = new APIError("Internal error happened (a Java exception happened)", 500);
                        listener.onErrorGot(error);
                        break;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return transactionTeasers;
        }

        @Override
        protected void onPostExecute(ArrayList<TransactionTeaser> accountTeasers) {
            listener.onListGotSuccessfully(accountTeasers);
            super.onPostExecute(transactionTeasers);
        }
    }
}
