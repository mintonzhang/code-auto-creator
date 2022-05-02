package cn.minsin.cg.override;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.select.BranchBuilder;
import com.baomidou.mybatisplus.generator.config.converts.select.Selector;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

import static com.baomidou.mybatisplus.generator.config.rules.DbColumnType.*;

/**
 * @author minton.zhang
 * @since 2020/12/9 下午4:20
 */
public class Oracle12CTypeConvert extends OracleTypeConvert {

	@Override
	public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {

		return use(fieldType)
				.test(containsAny("char", "clob").then(STRING))
				.test(containsAny("date", "timestamp").then(p -> toDateType(config)))
				.test(contains("number").then(Oracle12CTypeConvert::toNumberType))
				.test(contains("float").then(FLOAT))
				.test(contains("blob").then(BLOB))
				.test(containsAny("binary", "raw").then(BYTE_ARRAY))
				.or(STRING);
	}

	private static IColumnType toNumberType(String typeName) {
		if (typeName.matches("number\\([0-9]\\)") || "number".equalsIgnoreCase(typeName)) {
			return DbColumnType.INTEGER;
		} else if (typeName.matches("number\\([1-3][0-9]\\)")) {
			return DbColumnType.LONG;
		}
		return DbColumnType.BIG_DECIMAL;
	}

	static BranchBuilder<String, IColumnType> containsAny(CharSequence... values) {
		return BranchBuilder.of(s -> {
			for (CharSequence value : values) {
				if (s.contains(value)) {
					return true;
				}
			}
			return false;
		});
	}


	/**
	 * 使用指定参数构建一个选择器
	 *
	 * @param param 参数
	 * @return 返回选择器
	 */
	static Selector<String, IColumnType> use(String param) {
		return new Selector<>(param.toLowerCase());
	}

	/**
	 * 这个分支构建器用于构建用于支持 {@link String#contains(CharSequence)} 的分支
	 *
	 * @param value 分支的值
	 * @return 返回分支构建器
	 * @see #containsAny(CharSequence...)
	 */
	static BranchBuilder<String, IColumnType> contains(CharSequence value) {
		return BranchBuilder.of(s -> s.contains(value));
	}
}
