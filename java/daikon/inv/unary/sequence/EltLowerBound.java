package daikon.inv.unary.sequence;

import daikon.*;
import daikon.inv.*;
import daikon.inv.unary.sequence.*;
import daikon.inv.unary.scalar.*;
import daikon.inv.unary.*;
import daikon.inv.binary.sequenceScalar.*;
import daikon.inv.binary.twoSequence.*;
import daikon.derive.unary.*;
import utilMDE.*;

import java.util.*;

// *****
// Do not edit this file directly:
// it is automatically generated from Bound.java.jpp
// *****

// One reason not to combine LowerBound and Upperbound is that they have
// separate justifications:  one may be justified when the other is not.

// What should we do if there are few values in the range?
// This can make justifying that invariant easier, because with few values
// naturally there are more instances of each value.
// This might also make justifying that invariant harder, because to get more
// than (say) twice the expected number of samples (under the assumption of
// uniform distribution) requires many samples.
// Which of these dominates?  Is the behavior what I want?

public class EltLowerBound  extends SingleSequence  {

  public LowerBoundCore  core;

  private EltLowerBound (PptSlice ppt) {
    super(ppt);
    core = new LowerBoundCore (this);
  }

  public static EltLowerBound  instantiate(PptSlice ppt) {
    return new EltLowerBound (ppt);
  }

  public String repr() {
    return "EltLowerBound"  + varNames() + ": "
      + core.repr();
  }

  // ELTLOWEr || ELTUPPEr
  public String format() {
    return var().name.name() + " elements >= " + core.min1 ;
  }

  public String format_esc() {
    String[] form =
      VarInfoName.QuantHelper.format_esc(new VarInfoName[]
	{ var().name });
    return form[0] + "(" + form[1] + " >= " + core.min1  + ")" + form[2];
  }

  public String format_simplify() {
    String[] form =
      VarInfoName.QuantHelper.format_simplify(new VarInfoName[]
	{ var().name });
    return form[0] + "(>= " + form[1] + " " + core.min1  + ")" + form[2];
  }

  public void add_modified(long[]  value, int count) {
    // System.out.println("EltLowerBound"  + varNames() + ": "
    //                    + "add(" + value + ", " + modified + ", " + count + ")");

    for (int i=0; i<value.length; i++) {
      core.add_modified(value[i], count);
      if (no_invariant)
        return;
    }

  }

  public boolean enoughSamples() {
    return core.enoughSamples();
  }

  protected double computeProbability() {
    return core.computeProbability();
  }

  public boolean isExact() {
    return core.isExact();
  }

  public boolean isSameFormula(Invariant other)
  {
    return core.isSameFormula(((EltLowerBound ) other).core);
  }

  public boolean isObviousDerived() {
    VarInfo v = var();
    if (v.isDerived() && (v.derived instanceof SequenceLength)) {
      int vshift = ((SequenceLength) v.derived).shift;
      if (vshift != 0) {
        return true;

      }
    }

    // For each sequence variable, if this is an obvious member/subsequence, and
    // it has the same invariant, then this one is obvious.
    PptTopLevel pptt = (PptTopLevel) ppt.parent;
    for (int i=0; i<pptt.var_infos.length; i++) {
      VarInfo vi = pptt.var_infos[i];

      if (SubSequence.isObviousDerived(v, vi))

      {
        PptSlice1 other_slice = pptt.findSlice(vi);
        if (other_slice != null) {
          EltLowerBound  eb = EltLowerBound .find(other_slice);
          if ((eb != null)
              && eb.enoughSamples()
              && eb. core.min1  == core.min1 ) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public boolean isExclusiveFormula(Invariant other) {
    if (other instanceof EltUpperBound ) {
      if (core.min1  >  ((EltUpperBound ) other). core.max1 )
        return true;
    }
    if (other instanceof OneOfScalar) {
      return other.isExclusiveFormula(this);
    }
    return false;
  }

  // Look up a previously instantiated invariant.
  public static EltLowerBound  find(PptSlice ppt) {
    Assert.assert(ppt.arity == 1);
    for (Iterator itor = ppt.invs.iterator(); itor.hasNext(); ) {
      Invariant inv = (Invariant) itor.next();
      if (inv instanceof EltLowerBound )
        return (EltLowerBound ) inv;
    }
    return null;
  }

}

