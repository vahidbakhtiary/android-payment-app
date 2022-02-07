package ir.sep.android.merchantapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JsonParserHelper {

    @Inject
    public JsonParserHelper() {
    }

    public <T> ArrayList<T> convertJsonToObject(Class<T> klass, String json) throws Exception {
        json = json.replace("\"\\\\\"", "");

        Gson gson = new Gson();
        return gson.fromJson(json, new ListOfSomething<T>(klass));

    }

    public <T> ArrayList<T> convertJsonToObjectTest(Class<T> klass, String json) throws Exception {
        json = json.replace("\"\\\\\"", "");

        GsonBuilder gson = new GsonBuilder();
        Type collectionType = new TypeToken<ArrayList<T>>(){}.getType();

        ArrayList<T> myJson = gson.create().fromJson(json, collectionType);


        return myJson;
    }
    class ListOfSomething<X> implements ParameterizedType {

        private Class<?> wrapped;

        public ListOfSomething(Class<X> wrapped) {
            this.wrapped = wrapped;
        }

        public Type[] getActualTypeArguments() {
            return new Type[]{wrapped};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }

    }
}

