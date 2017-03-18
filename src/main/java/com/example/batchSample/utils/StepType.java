package com.example.batchSample.utils;

import org.springframework.batch.core.job.flow.FlowStep;
import org.springframework.batch.core.partition.support.PartitionStep;
import org.springframework.batch.core.step.job.JobStep;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.util.StringUtils;

/**
 * The types of Spring Batch {@link org.springframework.batch.core.Step} implementations that are known to the system.
 *
 */
public enum StepType {
	/**
	 * {@link TaskletStep}
	 */
	TASKLET_STEP(TaskletStep.class.getName(), "Tasklet Step"),

	/**
	 * {@link FlowStep}
	 */
	FLOW_STEP(FlowStep.class.getName(), "Flow Step"),

	/**
	 * {@link JobStep}
	 */
	JOB_STEP(JobStep.class.getName(), "Job Step"),

	/**
	 * {@link PartitionStep}
	 */
	PARTITION_STEP(PartitionStep.class.getName(), "Partition Step"),

	/**
	 * Used when the type of step is unknown to the system.
	 */
	UNKNOWN("", "");

	private final String className;
	private final String displayName;

	private StepType(String className, String displayName) {
		this.className = className;
		this.displayName = displayName;
	}

	/**
	 * @param className the fully qualified name of the {@link org.springframework.batch.core.Step} implementation
	 * @return the type if known, otherwise {@link #UNKNOWN}
	 */
	public static StepType fromClassName(String className) {
		StepType type = UNKNOWN;

		if (StringUtils.hasText(className)) {
			String name = className.trim();

			for (StepType curType : values()) {
				if (curType.className.equals(name)) {
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
