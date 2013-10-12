package com.augurworks.web.query;

import java.util.ArrayList;

public class InfiniteQuery {
	private static final String OUTPUT_STRING = "\"output\": {\"format\": \"json\"}";
	private final ArrayList<InfiniteQueryTerm> terms;
	private final String logic;

	private InfiniteQuery(ArrayList<InfiniteQueryTerm> terms, String logic) {
		this.terms = terms;
		this.logic = logic;
	}

	public String getQuery() {
		StringBuilder sb = new StringBuilder("{\"qt\": [");
		for (InfiniteQueryTerm qt : terms) {
			sb = sb.append(qt.getQueryTerm());
			sb = sb.append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb = sb.append("],");
		sb = sb.append("\"logic\": ").append("\"" + logic + "\"").append(",");
		sb = sb.append(OUTPUT_STRING);
		sb = sb.append("}");
		return sb.toString();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private final ArrayList<InfiniteQueryTerm> terms;
		private final ArrayList<String> logic;

		public Builder() {
			this.terms = new ArrayList<InfiniteQueryTerm>();
			this.logic = new ArrayList<String>();
		}

		public Builder withTerm(InfiniteQueryTerm term) {
			this.terms.add(term);
			this.logic.add("" + terms.size());
			return this;
		}

		public Builder and(int number) {
			constraint("AND", number);
			return this;
		}

		public Builder or(int number) {
			constraint("OR", number);
			return this;
		}

		private void constraint(String word, int number) {
			String temp = "";
			for (int i = logic.size() - 1; number > 0; number--, i--) {
				temp += logic.get(i) + " " + word + " ";
				logic.remove(i);
			}
			temp = temp.substring(0, temp.length() - 4);
			logic.add("(" + temp + ")");
		}

		public Builder not() {
			String temp = logic.get(logic.size() - 1);
			temp = "NOT " + temp;
			logic.remove(logic.size() - 1);
			logic.add(temp);
			return this;
		}

		public String getLogic() {
			StringBuilder sb = new StringBuilder();
			for (String s : logic) {
				sb = sb.append(s);
			}
			return sb.toString();
		}
		public InfiniteQuery build() {
			if (logic.size() != 1) {
				throw new IllegalArgumentException("You did not fully specify the composition logic.");
			}
			return new InfiniteQuery(terms, getLogic());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logic == null) ? 0 : logic.hashCode());
		result = prime * result + ((terms == null) ? 0 : terms.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "InfiniteQuery [terms=" + terms + ", logic=" + logic + "]";
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
		InfiniteQuery other = (InfiniteQuery) obj;
		if (logic == null) {
			if (other.logic != null) {
				return false;
			}
		} else if (!logic.equals(other.logic)) {
			return false;
		}
		if (terms == null) {
			if (other.terms != null) {
				return false;
			}
		} else if (!terms.equals(other.terms)) {
			return false;
		}
		return true;
	}
}
