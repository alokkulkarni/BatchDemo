package com.example.batchSample.utils;

import org.springframework.batch.core.step.item.ChunkOrientedTasklet;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.util.StringUtils;

/**
 * Types of {@link org.springframework.batch.core.step.tasklet.Tasklet} implementations
 * known by Spring Task.  These
 * include tasklets provided by Spring Batch and Spring for Apache Hadoop.
 */
public enum TaskletType {

	/**
	 * {@link ChunkOrientedTasklet}
	 */
	CHUNK_ORIENTED_TASKLET(ChunkOrientedTasklet.class.getName(), "Chunk Oriented Step"),
	/**
	 * {@link SystemCommandTasklet}
	 */
	SYSTEM_COMMAND_TASKLET(SystemCommandTasklet.class.getName(), "System Command Step"),
	/**
	 * {@link CallableTaskletAdapter}
	 */
	CALLABLE_TASKLET_ADAPTER(CallableTaskletAdapter.class.getName(), "Callable Tasklet Adapter Step"),
	/**
	 * {@link MethodInvokingTaskletAdapter}
	 */
	METHOD_INVOKING_TASKLET_ADAPTER(MethodInvokingTaskletAdapter.class.getName(), "Method Invoking Tasklet Adapter Step"),
	/**
	 * Used when the type of tasklet is unknown to the system
	 */
	UNKNOWN("", "");

	//TODO: Add Hadoop Types

	private final String className;
	private final String displayName;

	private TaskletType(String className, String displayName) {
		this.className = className;
		this.displayName = displayName;
	}

	/**
	 * @param className the fully qualified name of the {@link org.springframework.batch.core.step.tasklet.Tasklet}
	 *                     implementation
	 * @return the type if known, otherwise {@link #UNKNOWN}
	 */
	public static TaskletType fromClassName(String className) {
		TaskletType type = UNKNOWN;

		if(StringUtils.hasText(className)) {
			String name = className.trim();

			for (TaskletType curType : values()) {
				if(curType.className.equals(name)) {
					type = curType;
					break;
				}
			}
		}

		return type;
	}

	/**
	 * @return the name of the class the current value represents
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * @return the value to display in the UI or return via the REST API
	 */
	public String getDisplayName() {
		return this.displayName;
	}
}
