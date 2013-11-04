package com.augurworks.web.query;

import java.util.Date;

public class TimeConstraint {
	private Date min;
	private Date max;

	public TimeConstraint(Date min, Date max) {
		if (min != null) {
			this.min = new Date(min.getTime());
		}
		if (max != null) {
			this.max = new Date(max.getTime());
		}
	}

	public Date getMin() {
		if (min == null) {
			return null;
		}
		return new Date(min.getTime());
	}

	public Date getMax() {
		if (max == null) {
			return null;
		}
		return new Date(max.getTime());
	}

	public String formatForQuery() {
		if (min == null && max == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder("\"time\": {");
		if (min != null) {
			sb = sb.append("\"min\": ");
			sb = sb.append(min.getTime()).append(",");
		}
		if (max != null) {
			sb = sb.append("\"max\": ");
			sb = sb.append(max.getTime());
		} else {
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		sb = sb.append("}");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((max == null) ? 0 : max.hashCode());
		result = prime * result + ((min == null) ? 0 : min.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "TimeConstraint [min=" + min + ", max=" + max + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TimeConstraint other = (TimeConstraint) obj;
		if (max == null) {
			if (other.max != null) {
				return false;
			}
		} else if (!max.equals(other.max)) {
			return false;
		}
		if (min == null) {
			if (other.min != null) {
				return false;
			}
		} else if (!min.equals(other.min)) {
			return false;
		}
		return true;
	}


}
