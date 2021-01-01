/*
 * Dynamic Surroundings
 * Copyright (C) 2020  OreCruncher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package org.orecruncher.lib.config;

import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public final class ConfigProperty {

    private final ForgeConfigSpec.ValueSpec valueSpec;
    private final String name;

    private ConfigProperty(@Nonnull final ForgeConfigSpec spec, @Nonnull final ForgeConfigSpec.ConfigValue<?> configEntry) {
        final List<String> path = configEntry.getPath();
        this.valueSpec = spec.get(path);
        this.name = path.get(path.size() - 1);
    }

    public String getTranslationKey() {
        return this.valueSpec.getTranslationKey();
    }

    @Nonnull
    public ITextComponent getConfigName() {
        final String key = getTranslationKey();
        if (StringUtils.isNullOrEmpty(key)) {
            return new StringTextComponent(this.name);
        }

        return new TranslationTextComponent(key);
    }

    @Nullable
    public String getComment() {
        return this.valueSpec.getComment();
    }

    @Nullable
    public ITextComponent getTooltip() {
        String key = getTranslationKey();
        if (StringUtils.isNullOrEmpty(key)) {
            key = getComment();
            if (StringUtils.isNullOrEmpty(key))
                return null;
            return new StringTextComponent(key);
        }
        return new TranslationTextComponent(key + ".tooltip");
    }

    public boolean getNeedsWorldRestart() {
        return this.valueSpec.needsWorldRestart();
    }

    @Nonnull
    public static <T> ConfigProperty getPropertyInfo(@Nonnull final ForgeConfigSpec spec, @Nonnull final ForgeConfigSpec.ConfigValue<T> configEntry) {
        return new ConfigProperty(spec, configEntry);
    }

}