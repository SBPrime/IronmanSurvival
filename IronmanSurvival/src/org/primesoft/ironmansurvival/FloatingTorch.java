/*
 * The MIT License
 *
 * Copyright 2014 SBPrime.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primesoft.ironmansurvival;

import java.util.List;
import net.minecraft.server.v1_7_R1.BlockTorch;
import net.minecraft.server.v1_7_R1.World;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.ChatPaginator;

/**
 *
 * @author SBPrime
 */
public class FloatingTorch extends BlockTorch {

    public static final String FLOATING_TORCH = "FLOATING_TORCH_DATA";

    public FloatingTorch() {
        super();

        c(0.0F);
        a(0.9375F);
        a(f);
        c("torch");
        d("torch_on");
    }

    public static void registerEntity() {
        REGISTRY.a(50, "floating_torch", new FloatingTorch());
    }

    @Override
    public boolean canPlace(World world, int x, int y, int z) {
        List<MetadataValue> data = world.getWorld().getBlockAt(x, y, z).getMetadata(FLOATING_TORCH);
        if (data != null && !data.isEmpty()) {
            return true;
        }        

        return super.canPlace(world, x, y, z);
    }

}
