package com.bunq.tinker;

import com.bunq.sdk.context.ApiEnvironmentType;
import com.bunq.sdk.model.core.NotificationFilterUrlUserInternal;
import com.bunq.sdk.model.generated.endpoint.NotificationFilterUrl;
import com.bunq.sdk.model.generated.object.NotificationFilterUrlObject;
import com.bunq.tinker.libs.BunqLib;
import com.bunq.tinker.libs.SharedLib;
import com.bunq.tinker.utils.ITinker;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class AddCallbackUrl implements ITinker {

    /**
     * Notification filter constants.
     */
    private static final String NOTIFICATION_CATEGORY_MUTATION = "MUTATION";

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

        String callbackUrl = SharedLib.determineCallbackUrlFromAllOptionOrStdIn(allOption);

        System.out.println();
        System.out.println("  | Adding Callback URL:    " + callbackUrl);
        System.out.println();
        System.out.println("    ...");
        System.out.println();

        List<NotificationFilterUrlObject> allNotificationFilterCurrent = NotificationFilterUrl.list().getValue();
        List<NotificationFilterUrlObject> allNotificationFilterUpdated = new ArrayList<>();

        for (NotificationFilterUrlObject notificationFilterUrlUser : allNotificationFilterCurrent) {
                if (callbackUrl.equals(notificationFilterUrlUser.getNotificationTarget())) {
                    allNotificationFilterUpdated.add(notificationFilterUrlUser);
                }
        }

        allNotificationFilterUpdated.add(
                new NotificationFilterUrlObject(NOTIFICATION_CATEGORY_MUTATION, callbackUrl)
        );

        NotificationFilterUrlUserInternal.createWithListResponse(allNotificationFilterUpdated);

        System.out.println();
        System.out.println("  | ✅  Callback URL added");
        System.out.println();
        System.out.println("  | ▶️  Check your changed overview");
        System.out.println();
        System.out.println();

        bunq.updateContext();
    }
}
