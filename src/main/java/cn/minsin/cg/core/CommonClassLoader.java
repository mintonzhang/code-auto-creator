package cn.minsin.cg.core;

import cn.minsin.cg.Launcher;

/**
 * @author minsin/mintonzhang@163.com
 * @since 2022/5/2
 */
public class CommonClassLoader {

    public static ClassLoader getClassLoader() {
        return Launcher.class.getClassLoader();
    }
}
