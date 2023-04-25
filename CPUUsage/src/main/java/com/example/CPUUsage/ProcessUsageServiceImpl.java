package com.example.CPUUsage;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;

import org.springframework.stereotype.Service;

@Service
public class ProcessUsageServiceImpl implements ProcessUsageService {
    @Override
    public ProcessUsage getProcessUsage(String pidOrName) {
        double cpuUsage = 0;
        long memoryUsage = 0;

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            @SuppressWarnings("restriction")
			com.sun.management.OperatingSystemMXBean sunOsBean = (com.sun.management.OperatingSystemMXBean) osBean;
            long processCpuTime = 0;
            if (pidOrName.matches("\\d+")) {
                processCpuTime = sunOsBean.getProcessCpuTime();
            } else {
            	
				/*
				 * for (com.sun.management.OperatingSystemMXBean.ProcessInfo info :
				 * sunOsBean.getProcessInfo()) { if (info.getName().equalsIgnoreCase(pidOrName))
				 * { processCpuTime = info.getProcessCpuTime(); break; } }
				 */
            }
            long upTime = sunOsBean.getProcessCpuTime() / 1000000L;
            cpuUsage = 100.0 * processCpuTime / (upTime * sunOsBean.getAvailableProcessors() * 1000000L);
        }

        for (java.lang.management.MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (pool.getType() == java.lang.management.MemoryType.HEAP) {
                MemoryUsage usage = pool.getUsage();
                memoryUsage += usage.getUsed();
            }
        }

        return new ProcessUsage(cpuUsage, memoryUsage);
    }
}
