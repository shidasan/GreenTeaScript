import java.util.ArrayList;

//GreenTea Generator should be written in each language.

public class BashSourceGenerator extends SourceGenerator {
	BashSourceGenerator() {
		super("BashSource");
	}

	public void VisitEach(TypedNode Node) {
		String Code = "\n";
		this.Indent();
		/*local*/TypedNode CurrentNode = Node;
		while(CurrentNode != null) {
			CurrentNode.Evaluate(this);
			Code += this.GetIndentString() + this.PopSourceCode() + "\n";
			CurrentNode = CurrentNode.NextNode;
		}
		this.UnIndent();
		Code += this.GetIndentString();
		this.PushSourceCode(Code);
	}

	@Override public void VisitEmptyNode(TypedNode Node) {
	}

	@Override public void VisitIndexerNode(IndexerNode Node) {
		Node.Indexer.Evaluate(this);
		Node.Expr.Evaluate(this);
		this.PushSourceCode("${" + this.PopSourceCode() + "[" + this.PopSourceCode() + "]}");
	}

	@Override public void VisitMessageNode(MessageNode Node) {
		// do nothing
	}

	@Override public void VisitWhileNode(WhileNode Node) {
//		Node.CondExpr.Evaluate(this);
//		String Program = "while(" + this.PopSourceCode() + ")";
//		this.VisitEach(Node.LoopBody);
//		Program += this.PopSourceCode();
//		this.UnIndent();
//		this.PushSourceCode(Program);	//TODO
	}

	@Override public void VisitDoWhileNode(DoWhileNode Node) {
//		String Program = "do";
//		this.VisitEach(Node.LoopBody);
//		Node.CondExpr.Evaluate(this);
//		Program += " while(" + this.PopSourceCode() + ")";
//		this.PushSourceCode(Program);
	}

	@Override public void VisitForNode(ForNode Node) {
//		Node.IterExpr.Evaluate(this);
//		Node.CondExpr.Evaluate(this);
//		Node.InitNode.Evaluate(this);
//		String Init = this.PopSourceCode();
//		String Cond = this.PopSourceCode();
//		String Iter = this.PopSourceCode();
//
//		String Program = "for(" + Init + "; " + Cond  + "; " + Iter + ")";
//		Node.LoopBody.Evaluate(this);
//		Program += this.PopSourceCode();
//		this.PushSourceCode(Program);	//TODO
	}

	@Override public void VisitForEachNode(ForEachNode Node) {
		// do nothing
	}
//
	@Override public void VisitConstNode(ConstNode Node) {
		this.PushSourceCode(Node.ConstValue.toString());
	}
//
	@Override public void VisitNewNode(NewNode Node) {
//		String Type = Node.Type.ShortClassName;
//		this.PushSourceCode("new " + Type);
	}
//
	@Override public void VisitNullNode(NullNode Node) {
		this.PushSourceCode("0");
	}
//
	@Override public void VisitLocalNode(LocalNode Node) {
		this.PushSourceCode("$" + Node.LocalName);
	}
//
//	@Override
//	public void VisitGetterNode(GetterNode Node) {
//		Node.Expr.Evaluate(this);
//		this.PushSourceCode(this.PopSourceCode() + "." + Node.Method.MethodName);
//	}
//
//	private String[] EvaluateParam(ArrayList<TypedNode> Params) {
//		int Size = Params.size();
//		String[] Programs = new String[Size];
//		for(int i = 0; i < Size; i++) {
//			TypedNode Node = Params.get(i);
//			Node.Evaluate(this);
//			Programs[Size - i - 1] = this.PopSourceCode();
//		}
//		return Programs;
//	}
//
//	@Override
//	public void VisitApplyNode(ApplyNode Node) {
//		/*local*/String Program = Node.Method.MethodName + "(";
//		/*local*/String[] Params = EvaluateParam(Node.Params);
//		for(int i = 0; i < Params.length; i++) {
//			String P = Params[i];
//			if(i != 0) {
//				Program += ",";
//			}
//			Program += P;
//		}
//		Program += ")";
//		this.PushSourceCode(Program);
//	}

	@Override public void VisitSuffixNode(SuffixNode Node) {
		String MethodName = Node.Token.ParsedText;
		if(MethodName.equals("++")) {
		}
		else if(MethodName.equals("--")) {
		}
		else {
			throw new RuntimeException("NotSupportOperator: " + MethodName);
		}
		Node.Expr.Evaluate(this);
		this.PushSourceCode("$((" + this.PopSourceCode() + MethodName + "))");
	}

	@Override public void VisitUnaryNode(UnaryNode Node) {
		String MethodName = Node.Token.ParsedText;
		String UnaryOp = MethodName;
		boolean IsReturnable = true;

		if(MethodName.equals("+")) {
			UnaryOp = "1 * ";
		}
		else if(MethodName.equals("-")) {
			UnaryOp = "-1 * ";
		}
		else if(MethodName.equals("~")) {
		}
		else if(MethodName.equals("!")) {
			UnaryOp = "!";
			IsReturnable = false;
		}
		else if(MethodName.equals("++")) {
			UnaryOp = "++";
		}
		else if(MethodName.equals("--")) {
			UnaryOp = "--";
		}
		else {
			throw new RuntimeException("NotSupportOperator: " + UnaryOp);
		}
		Node.Expr.Evaluate(this);

		String Head = "";
		String Tail = "";
		if(IsReturnable) {
			Head = "$((";
			Tail = "))";
		}
		this.PushSourceCode(Head + UnaryOp + this.PopSourceCode() + Tail);
	}

	@Override public void VisitBinaryNode(BinaryNode Node) {
		String MethodName = Node.Token.ParsedText;
		String BinaryOp = MethodName;
		boolean IsReturnable = true;

		if(MethodName.equals("+")) {
		}
		else if(MethodName.equals("-")) {
		}
		else if(MethodName.equals("*")) {
		}
		else if(MethodName.equals("/")) {
		}
		else if(MethodName.equals("%")) {
		}
		else if(MethodName.equals("<<")) {
		}
		else if(MethodName.equals(">>")) {
		}
		else if(MethodName.equals("&")) {
		}
		else if(MethodName.equals("|")) {
		}
		else if(MethodName.equals("^")) {
		}
		else if(MethodName.equals("<=")) {
			BinaryOp = "-le";
			IsReturnable = false;
		}
		else if(MethodName.equals("<")) {
			BinaryOp = "-lt";
			IsReturnable = false;
		}
		else if(MethodName.equals(">=")) {
			BinaryOp = "-ge";
			IsReturnable = false;
		}
		else if(MethodName.equals(">")) {
			BinaryOp = "-gt";
			IsReturnable = false;
		}
		else if(MethodName.equals("!=")) {
			BinaryOp = "-eq";
			IsReturnable = false;
		}
		else if(MethodName.equals("==")) {
			BinaryOp = "-eq";
			IsReturnable = false;
		}
		else {
			throw new RuntimeException("NotSupportOperator: " + BinaryOp);
		}
		Node.RightNode.Evaluate(this);
		Node.LeftNode.Evaluate(this);

		String Head = "";
		String Tail = "";
		if(IsReturnable) {
			Head = "$((";
			Tail = "))";
		}
		this.PushSourceCode(Head + this.PopSourceCode() + " " + BinaryOp + " " + this.PopSourceCode() + Tail);
	}

	@Override public void VisitAndNode(AndNode Node) {
		Node.RightNode.Evaluate(this);
		Node.LeftNode.Evaluate(this);
		this.PushSourceCode(this.PopSourceCode() + " && " + this.PopSourceCode());
	}

	@Override public void VisitOrNode(OrNode Node) {
		Node.RightNode.Evaluate(this);
		Node.LeftNode.Evaluate(this);
		this.PushSourceCode(this.PopSourceCode() + " || " + this.PopSourceCode());
	}

	@Override public void VisitAssignNode(AssignNode Node) {	//FIXME: support $, ${}
		Node.RightNode.Evaluate(this);
		Node.LeftNode.Evaluate(this);
		this.PushSourceCode(this.PopSourceCode() + "=" + this.PopSourceCode());
	}

	@Override public void VisitLetNode(LetNode Node) {
//		String Type = Node.DeclType.ShortClassName;
//		Node.VarNode.Evaluate(this);
//		String Code = Type + " " + this.PopSourceCode();
//		Node.BlockNode.Evaluate(this);
//		this.PushSourceCode(Code + this.PopSourceCode());
	}

	@Override public void VisitIfNode(IfNode Node) {
		Node.CondExpr.Evaluate(this);
		this.VisitEach(Node.ThenNode);
		this.VisitEach(Node.ElseNode);

		String ElseBlock = this.PopSourceCode();
		String ThenBlock = this.PopSourceCode();
		String CondExpr = this.PopSourceCode();
		String Code = "if " + CondExpr + "\nthen" + ThenBlock;
		if(Node.ElseNode != null) {
			Code += "\nelse" + ElseBlock;
		}
		Code += "fi";
		this.PushSourceCode(Code);
	}

	@Override public void VisitSwitchNode(SwitchNode Node) {

	}

	@Override public void VisitReturnNode(ReturnNode Node) {	// only support int value
		String Code = "return";
		if(Node.Expr != null) {
			Node.Expr.Evaluate(this);
			Code += " " + this.PopSourceCode();
		}
		this.PushSourceCode(Code);
	}

	@Override public void VisitLabelNode(LabelNode Node) {
//		String Label = Node.Label;
//		this.PushSourceCode(Label + ":");
	}

	@Override public void VisitJumpNode(JumpNode Node) {
//		String Label = Node.Label;
//		this.PushSourceCode("goto " + Label);
	}

	@Override public void VisitBreakNode(BreakNode Node) {
		String Code = "break";
//		String Label = Node.Label;
//		if(Label != null) {		// not support label
//			Code += " " + Label;
//		}
		this.PushSourceCode(Code);
	}

	@Override public void VisitContinueNode(ContinueNode Node) {
		String Code = "continue";
//		String Label = Node.Label;
//		if(Label != null) {		// not support label
//			Code += " " + Label;
//		}
		this.PushSourceCode(Code);
	}

	@Override public void VisitTryNode(TryNode Node) {
//		String Code = "try";
//		//this.VisitEach(Node.CatchBlock);
//		this.VisitEach(Node.TryBlock);
//		Code += this.PopSourceCode();
//		if(Node.FinallyBlock != null) {
//			this.VisitEach(Node.FinallyBlock);
//			Code += " finally " + this.PopSourceCode();
//		}
//		this.PushSourceCode(Code);
	}

	@Override public void VisitThrowNode(ThrowNode Node) {
//		Node.Expr.Evaluate(this);
//		String Code = "throw " + this.PopSourceCode();
//		this.PushSourceCode(Code);
	}

	@Override public void VisitFunctionNode(FunctionNode Node) {
		// TODO Auto-generated method stub
	}

	@Override public void VisitErrorNode(ErrorNode Node) {
//		String Code = "throw Error(\"" + Node.Token.ParsedText + "\")";
//		this.PushSourceCode(Code);
	}

	private TypedNode ResolveParamName(ArrayList<String> ParamNameList, TypedNode Body) {
		TypedNode resolvedBody = CreateEmptyNode(null, null);
		TypedNode nextNode = resolvedBody.NextNode;
		int size = ParamNameList.size();
		for(int i = 0; i < size; i++) {
			TypedNode leftNode = CreateLocalNode(null, null, ParamNameList.get(i));
			TypedNode rightNode = CreateLocalNode(null, null, "$" + (i + 1));
			nextNode = CreateAssignNode(null, null, leftNode, rightNode);
			nextNode = nextNode.NextNode;
		}
		nextNode = Body;
		return resolvedBody;
	}

	@Override public void DefineFunction(GtMethod Method, ArrayList<String> ParamNameList, TypedNode Body) {
		String Function = "function ";
		Function += Method.MethodName + "() {";
		Function += Eval(ResolveParamName(ParamNameList, Body));
		Function += "\n}";
		PushSourceCode(Function);
		this.WriteTranslatedCode(Function);
	}

	@Override public Object Eval(TypedNode Node) {
		this.VisitEach(Node);
		return this.PopSourceCode();
	}

	@Override public void AddClass(GtType Type) {
	}

	@Override public void SetLanguageContext(GtContext Context) {
		new JavaLayerDef().MakeDefinition(Context.DefaultNameSpace);
	}
}

