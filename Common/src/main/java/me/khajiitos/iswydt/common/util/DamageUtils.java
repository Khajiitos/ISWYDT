package me.khajiitos.iswydt.common.util;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class DamageUtils {

    public static Holder<DamageType> getDamageTypeHolder(ResourceKey<DamageType> resourceKey, RegistryAccess registryAccess) {
        Registry<DamageType> damageTypes = registryAccess.registryOrThrow(Registries.DAMAGE_TYPE);
        return damageTypes.getHolderOrThrow(resourceKey);
    }
}
