package ir.sep.android.merchantapp.utils;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GUIDHelper {

    @Inject
    public GUIDHelper() {
    }

    public String getGUID() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        return randomUUIDString;
    }
}
