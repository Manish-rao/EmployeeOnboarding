package com.emp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.emp.statemachine.EmployeeEvent;
import com.emp.statemachine.EmployeeState;

@Configuration
@EnableStateMachine
public class EmployeStateConfig extends EnumStateMachineConfigurerAdapter<EmployeeState, EmployeeEvent>{
	@Override
	public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions) throws Exception {
	  transitions
	   .withExternal()
	   .source(EmployeeState.ADDED)
	   .target(EmployeeState.IN_CHECK)
	   .event(EmployeeEvent.in_check)
	   .and()
	   .withExternal()
	   .source(EmployeeState.IN_CHECK)
	   .target(EmployeeState.APPROVED)
	   .event(EmployeeEvent.approve)
	   .and() 
	   .withExternal()
	   .source(EmployeeState.APPROVED)
	   .target(EmployeeState.ACTIVE)
	   .event(EmployeeEvent.activate);
	}
	
	@Override
    public void configure(
            StateMachineConfigurationConfigurer
                    <EmployeeState, EmployeeEvent> config) throws Exception {
        config.withConfiguration()  
              .autoStartup(true);
    }
	
	@Override
    public void configure(
            StateMachineStateConfigurer<EmployeeState, EmployeeEvent> states)
            throws Exception {
        states.withStates()		
              .initial(EmployeeState.ADDED)
              .state(EmployeeState.IN_CHECK)
              .state(EmployeeState.APPROVED)
              .end(EmployeeState.ACTIVE);
    }
}
