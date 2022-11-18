package com.bunq.tinker;

import com.bunq.sdk.context.ApiEnvironmentType;
import com.bunq.sdk.model.generated.endpoint.Card;
import com.bunq.sdk.model.generated.endpoint.MonetaryAccountBank;
import com.bunq.sdk.model.generated.endpoint.Payment;
import com.bunq.sdk.model.generated.endpoint.RequestInquiry;
import com.bunq.sdk.model.generated.endpoint.User;
import com.bunq.sdk.model.generated.object.Pointer;
import com.bunq.tinker.libs.BunqLib;
import com.bunq.tinker.libs.SharedLib;
import com.bunq.tinker.utils.ITinker;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.net.UnknownHostException;
import java.util.List;

public class UserOverview implements ITinker {

    /**
     * @param args
     *
     * @throws ParseException
     */
    public void run(String[] args) throws ParseException, UnknownHostException {
        CommandLine allOption = SharedLib.parseAllOption(args);
        ApiEnvironmentType environmentType = SharedLib.determineEnvironmentType(allOption);

        SharedLib.printHeader();

        BunqLib bunq = new BunqLib(environmentType);

        User user = bunq.getUser();
        SharedLib.printUser(user);

        List<MonetaryAccountBank> allMonetaryAccountBankActive = bunq.getAllMonetaryAccountBankActive(1);
        SharedLib.printAllMonetaryAccountBank(allMonetaryAccountBankActive);

        List<Payment> allPayment = bunq.getAllPayment(allMonetaryAccountBankActive.get(0), 1);
        SharedLib.printAllPayment(allPayment);

        List<RequestInquiry> allRequest = bunq.getAllRequest(allMonetaryAccountBankActive.get(0), 1);
        SharedLib.printAllRequest(allRequest);

        List<Card> allCard = bunq.getAllCard(1);
        SharedLib.printAllCard(allCard, allMonetaryAccountBankActive);

        List<Pointer> allUserAlias = bunq.getAllUserAlias();
        SharedLib.printAllUserAlias(allUserAlias);

        System.out.println("");
        System.out.println("");
        System.out.println("        Want to see more monetary accounts, payments, requests or even cards?");
        System.out.println("                Adjust src/main/java/com/bunq/tinker/UserOverview.java accordingly.");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        bunq.updateContext();
    }
}
