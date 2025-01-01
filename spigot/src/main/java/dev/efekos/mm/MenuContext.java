/*
 * MIT License
 *
 * Copyright (c) 2025 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.mm;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

public class MenuContext {
    private static final Map<UUID, MenuContext> instances = new HashMap<>();
    private final Player owner;
    private final Stack<Menu> menuHistory = new Stack<>();
    private final Map<String, Object> data = new HashMap<>();

    private MenuContext(Player owner) {
        this.owner = owner;
    }

    public static MenuContext of(Player owner) {
        if (instances.containsKey(owner.getUniqueId())) return instances.get(owner.getUniqueId());
        MenuContext data = new MenuContext(owner);
        instances.put(owner.getUniqueId(), data);
        return data;
    }

    public Stack<Menu> getMenuHistory() {
        return menuHistory;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public Player getOwner() {
        return owner;
    }

    public void addMenu(Menu menu) {
        menuHistory.push(menu);
    }

    public Menu lastMenu() {
        menuHistory.pop();
        return menuHistory.pop();
    }

    public void setString(String key,String value) {
        set(key, value);
    }

    public String getString(String key) {
        return String.valueOf(get(key));
    }

    public void setUniqueId(String key, UUID uuid) {
        setString(key, uuid.toString());
    }

    public UUID getUniqueId(String key) {
        return UUID.fromString(getString(key));
    }

    public Player getPlayer(String key){
        return Bukkit.getPlayer(getUniqueId(key));
    }

    public void setPlayer(String key,Player player) {
        setUniqueId(key, player.getUniqueId());
    }

}
