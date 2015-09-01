package org.sdrc.evm.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.sdrc.evm.model.EvmDataNode;
import org.sdrc.evm.model.EvmQuestionModel;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class E6ServiceImpl implements E6Service {

	@Override
	public EvmQuestionModel calculateE6_16(Document document,
			EvmQuestion question, String xPathString)
			throws XPathExpressionException {

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();

		String valB = xPath.compile(
				xPathString + question.getXpath() + "/"
						+ question.getEvmSubQuestions().get(0).getXpath())
				.evaluate(document);
		String valC = xPath.compile(
				xPathString + question.getXpath() + "/"
						+ question.getEvmSubQuestions().get(1).getXpath())
				.evaluate(document);

		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valB);
		dataNodes.add(dataNode);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNode.setValue(valC);
		dataNodes.add(dataNode);

		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			if (Double.parseDouble(valC) < Double.parseDouble(valB)
					&& Double.parseDouble(valC) / Double.parseDouble(valB) <= 0.01) {
				evmQuestionModel.setValue(question.getWeightage());
				return evmQuestionModel;
			} else {
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}
		}

		catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}

	}

	@Override
	public EvmQuestionModel calculateE6_21(Document document,
			EvmQuestion question, String xPathString)
			throws XPathExpressionException {

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();

		String valA = xPath.compile(
				xPathString + question.getXpath() + "/"
						+ question.getEvmSubQuestions().get(0).getXpath())
				.evaluate(document);
		String valB = xPath.compile(
				xPathString + question.getXpath() + "/"
						+ question.getEvmSubQuestions().get(1).getXpath())
				.evaluate(document);

		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valA);
		dataNodes.add(dataNode);

		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNode.setValue(valB);
		dataNodes.add(dataNode);

		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);

			if (Double.parseDouble(valB) == 0 || Double.parseDouble(valA) == 0) {
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;
			}

			else {
				evmQuestionModel.setValue((Double.parseDouble(valB) > Double
						.parseDouble(valA) ? 1 : Double.parseDouble(valB)
						/ Double.parseDouble(valA))
						* question.getWeightage());
				return evmQuestionModel;
			}

		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}

	}

	@Override
	public EvmQuestionModel calculateE6_22(Document document, EvmQuestion question,
			String xPathString) throws XPathExpressionException {

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();

		String valA = xPath.compile(
				xPathString + question.getXpath() + "/"
						+ question.getEvmSubQuestions().get(0).getXpath())
				.evaluate(document);
		String valB = xPath.compile(
				xPathString + question.getXpath() + "/"
						+ question.getEvmSubQuestions().get(1).getXpath())
				.evaluate(document);
		
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valA);
		dataNodes.add(dataNode);

		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(1).getQuestionName());
		dataNode.setValue(valB);
		dataNodes.add(dataNode);

		
		try {
			Double result = Double.parseDouble(valB) > 0 && Double.parseDouble(valA) > 0 ? Double
					.parseDouble(valB) >= Math.max(4.0,
					12.0 / Double.parseDouble(valA)) ? 1 * question
					.getWeightage() : Double.parseDouble(valB)
					/ Math.max(4.0, 12.0 / Double.parseDouble(valA))
					* question.getWeightage() : 0;
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(result);
			return evmQuestionModel;
		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}

	}

	@Override
	public EvmQuestionModel calculateE6_23(Document document, EvmQuestion question,
			String xPathString) throws XPathExpressionException {

		EvmQuestionModel evmQuestionModel = new EvmQuestionModel();
		XPath xPath = XPathFactory.newInstance().newXPath();
		EvmDataNode dataNode = null;
		List<EvmDataNode> dataNodes = new ArrayList<>();

		String valG = xPath.compile(
				xPathString + question.getXpath() + "/"
						+ question.getEvmSubQuestions().get(0).getXpath())
				.evaluate(document);
		dataNode = new EvmDataNode();
		dataNode.setName(question.getEvmSubQuestions().get(0).getQuestionName());
		dataNode.setValue(valG);
		dataNodes.add(dataNode);

		try {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);

			if (Double.parseDouble(valG) > 0 || Double.parseDouble(valG) < 4){

				evmQuestionModel.setValue(Double.parseDouble(valG) * question.getWeightage() / 4);
				return evmQuestionModel;
			}
			else
				evmQuestionModel.setValue(0.0);
				return evmQuestionModel;

		} catch (Exception e) {
			evmQuestionModel.setName(question.getQuestionName());
			evmQuestionModel.setDataNodes(dataNodes);
			evmQuestionModel.setValue(0.0);
			return evmQuestionModel;
		}

	}
}