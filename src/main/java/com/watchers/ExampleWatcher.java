package com.watchers;

import com.annotations.Production;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Production
public class ExampleWatcher {

  @Scheduled(fixedDelayString = "${watcher.interval}")
  private void activateManager() {
    System.err.println("Watcher ping.");
  }
}
