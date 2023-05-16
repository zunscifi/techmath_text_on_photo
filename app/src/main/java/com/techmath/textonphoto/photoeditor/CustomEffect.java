package com.techmath.textonphoto.photoeditor;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

public class CustomEffect {
    private final String mEffectName;
    private final Map<String, Object> parametersMap;

    private CustomEffect(Builder builder) {
        this.mEffectName = builder.mEffectName;
        this.parametersMap = builder.parametersMap;
    }

    public String getEffectName() {
        return this.mEffectName;
    }

    public Map<String, Object> getParameters() {
        return this.parametersMap;
    }

    public static class Builder {

        public String mEffectName;

        public Map<String, Object> parametersMap = new HashMap<>();

        public Builder(@NonNull String str) throws RuntimeException {
            if (!TextUtils.isEmpty(str)) {
                this.mEffectName = str;
                return;
            }
            throw new RuntimeException("Effect name cannot be empty.Please provide effect name from EffectFactory");
        }

        public Builder setParameter(@NonNull String str, Object obj) {
            this.parametersMap.put(str, obj);
            return this;
        }

        public CustomEffect build() {
            return new CustomEffect(this);
        }
    }
}
