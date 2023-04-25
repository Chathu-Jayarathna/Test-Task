package com.example.CPUUsage;

import org.springframework.stereotype.Service;

public interface ProcessUsageService {
    ProcessUsage getProcessUsage(String pidOrName);
}

