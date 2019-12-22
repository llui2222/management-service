package com.tpam.service.management.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

interface StrategySink
{
  String STRATEGY_SUSPENDED_INPUT ="strategySuspendedInput";



  @Input(StrategySink.STRATEGY_SUSPENDED_INPUT)
  SubscribableChannel strategySuspendedInput();
}
