package com.vladooha.data.validation.grouping;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;


@GroupSequence({Default.class, FirstOrder.class, SecondOrder.class})
public interface ValidationSequence {
}
