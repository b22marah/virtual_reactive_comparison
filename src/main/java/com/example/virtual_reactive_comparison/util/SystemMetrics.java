package com.example.virtual_reactive_comparison.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import com.sun.management.OperatingSystemMXBean;

public class SystemMetrics {

    public static double getProcessCpuLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        return osBean.getProcessCpuLoad() * 100; // percentage
    }

    public static long getHeapMemoryUsage() {
        MemoryUsage heap = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        return heap.getUsed() / (1024 * 1024); // MB
    }
}
