package com.tle.web.sections.js.generic.statement;

import com.tle.web.sections.SectionsRuntimeException;
import com.tle.web.sections.events.PreRenderContext;
import com.tle.web.sections.events.RenderContext;
import com.tle.web.sections.js.JSCallable;
import com.tle.web.sections.js.JSExpression;

public class AssignAsFunction implements JSCallable
{
	protected final JSExpression var;
	private JSExpression value;

	public AssignAsFunction(JSExpression var)
	{
		this.var = var;
	}

	public AssignAsFunction(JSExpression var, JSExpression val)
	{
		this.var = var;
		this.value = val;
	}

	@Override
	public int getNumberOfParams(RenderContext context)
	{
		return value == null ? 1 : 0;
	}

	@SuppressWarnings("nls")
	@Override
	public String getExpressionForCall(RenderContext info, JSExpression... params)
	{
		if( params.length != getNumberOfParams(info) )
		{
			throw new SectionsRuntimeException("Wrong number of parameters for " + var.getExpression(info)
				+ ".  Expected " + getNumberOfParams(info) + " but was " + params.length);
		}
		JSExpression valexpr = value;
		if( valexpr == null )
		{
			valexpr = params[0];
		}
		return var.getExpression(info) + " = " + valexpr.getExpression(info); //$NON-NLS-1$
	}

	@Override
	public void preRender(PreRenderContext info)
	{
		info.preRender(var, value);
	}

}